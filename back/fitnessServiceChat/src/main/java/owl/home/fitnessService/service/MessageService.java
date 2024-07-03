package owl.home.fitnessService.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import owl.home.fitnessService.entity.Message;

import java.util.List;


@Component
public class MessageService {
    private final EntityManager entityManager;

    @Value("${messages.maxResult}")
    private int maxResult;

    @Autowired
    public MessageService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public List<Message> getMessageByPage(int pageNumber) {
        return entityManager
                .createQuery("select m from Message m order by m.date", Message.class)
                .setMaxResults(maxResult)
                .setFirstResult(pageNumber + 1)
                .getResultList();
    }

    @Transactional
    public int getMaxPageCount() {
        int messageCount = entityManager
                .createQuery("select count(m) from Message m order by m.date", Integer.class)
                .getMaxResults();

        return messageCount % 30 == 0 ? messageCount / 30 : messageCount / 30 + 1;
    }

    @Transactional(readOnly = true)
    public Message getOldestMessage() {
        return entityManager
                .createQuery("select m from Message m order by m.date desc", Message.class)
                .setMaxResults(1)
                .getResultList()
                .get(0);
    }

    @SneakyThrows
    @Transactional
    public Message saveMessage(String webSocketStringMessage) {
        ObjectMapper objectMapper = new ObjectMapper();

        Message message = objectMapper.readValue(webSocketStringMessage, Message.class);

        entityManager.persist(message);

        return message;
    }
}