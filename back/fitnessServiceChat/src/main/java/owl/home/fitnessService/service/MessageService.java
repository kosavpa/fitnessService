package owl.home.fitnessService.service;


import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.socket.WebSocketMessage;
import owl.home.fitnessService.entity.Message;

import java.util.List;


public class MessageService {
    private final EntityManager entityManager;

    @Value("${messages.maxResult}")
    private int maxResult;

    @Autowired
    public MessageService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Message> getMessageByPage(int pageNumber) {
        return entityManager
                .createQuery("select m from Message m order by m.date", Message.class)
                .setMaxResults(maxResult)
                .setFirstResult(pageNumber + 1)
                .getResultList();
    }

    public int getMaxPageCount() {
        int messageCount = entityManager
                .createQuery("select count(m) from Message m order by m.date", Integer.class)
                .getMaxResults();

        return messageCount % 30 == 0 ? messageCount / 30 : messageCount / 30 + 1;
    }

    public WebSocketMessage saveMessage(WebSocketMessage webSocketMessage) {
        //save

        return webSocketMessage;
    }
}