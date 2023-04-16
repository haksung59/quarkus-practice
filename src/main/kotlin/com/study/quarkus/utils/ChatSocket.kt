package com.study.quarkus.utils

import com.study.quarkus.repositories.TalkMsgRepository
import com.study.quarkus.repositories.TalkRoomRepository
import com.study.quarkus.repositories.TalkUserRepository
import com.study.quarkus.responses.TalkRoomResponse
import io.vertx.core.json.JsonArray
import io.vertx.core.json.JsonObject
import kotlinx.coroutines.*
import org.jboss.logging.Logger
import org.sbas.entities.talk.TalkMsg
import javax.inject.Inject
import javax.websocket.*
import javax.websocket.server.PathParam
import javax.websocket.server.ServerEndpoint


@ServerEndpoint("/chat-rooms/{tkrmId}/{userId}")
class ChatSocket {

    companion object {
        private val chatSockets = mutableMapOf<String, ChatSocket>() // WebSocket 연결을 관리할 Map
        private lateinit var talkMsg: MutableList<TalkMsg>
    }

    private lateinit var session: Session // WebSocket 세션
    private lateinit var tkrmId: String // 채팅방 ID
    private lateinit var userId: String // 사용자 ID

    @Inject
    lateinit var log: Logger

    @Inject
    lateinit var talkMsgRepository: TalkMsgRepository

    @OnOpen
    fun onOpen(session: Session, @PathParam("tkrmId") tkrmId: String, @PathParam("userId") userId: String) {
        this.session = session
        this.tkrmId = tkrmId
        this.userId = userId

        updateTalkMsg(tkrmId)

        val sendObject = JsonArray.of(talkMsg).toString()
        session.asyncRemote.sendText(sendObject)

        chatSockets[userId] = this // WebSocket 연결을 Map에 추가
    }

    @OnMessage
    fun onMessage(session: Session, message: String, @PathParam("tkrmId") tkrmId: String, @PathParam("userId") userId: String) {
        var addMsg = TalkMsg()
        runBlocking(Dispatchers.IO) {
            addMsg = talkMsgRepository.insertMessage(message, tkrmId, userId)
        }

        chatSockets.values // 모든 WebSocket 연결에 메시지 전송
            .filter { it.userId != userId && it.tkrmId == tkrmId }
            .forEach { it.session.asyncRemote.sendText(JsonObject.mapFrom(addMsg).toString()) }
    }

    @OnClose
    fun onClose(session: Session, @PathParam("userId") userId: String) {
        chatSockets.remove(userId) // WebSocket 연결을 Map에서 제거
    }

    fun updateTalkMsg(tkrmId: String) {
        val resultList = runBlocking {
            withContext(Dispatchers.IO) {
                talkMsgRepository.findChatDetail(tkrmId)
            }
        } as MutableList<TalkMsg>
        talkMsg = resultList
    }
}


@ServerEndpoint(value = "/chat-rooms/{userId}")
class ChatRoomEndpoint {

    companion object {
        lateinit var talkRooms: List<TalkRoomResponse>
    }

    @Inject
    private lateinit var log: Logger

    @Inject
    private lateinit var talkRoomRepository: TalkRoomRepository

    @Inject
    private lateinit var talkUserRepository: TalkUserRepository

    @OnOpen
    fun onOpen(session: Session, @PathParam("userId") userId: String) {
        updateTalkRooms(userId)
        val sendObject = JsonArray.of(talkRooms).toString()
        session.asyncRemote.sendText(sendObject)
    }

    @OnClose
    fun onClose(session: Session, @PathParam("userId") userId: String) {

    }

    private fun updateTalkRooms(userId: String){
        val resultList = runBlocking(Dispatchers.IO) {
            talkRoomRepository.findTalkRoomResponse(userId)
        }
        talkRooms = resultList
    }

}