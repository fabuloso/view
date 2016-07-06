import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;

public class BookEventsConsumer implements MessageListener {

    private final static Logger LOG = LoggerFactory.getLogger(BookEventsConsumer.class);

    private TopicConnection connection;

    public BookEventsConsumer() throws Exception {
        TopicConnectionFactory conFactory = new ActiveMQConnectionFactory("failover:(tcp://queue:61616)?initialReconnectDelay=100");
        connection = conFactory.createTopicConnection();
        TopicSession subSession = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic chatTopic = subSession.createTopic("events");
        TopicSubscriber subscriber = subSession.createSubscriber(chatTopic);
        subscriber.setMessageListener(this);
        connection.start();
    }

    public void onMessage(Message message) {
        try {
            TextMessage textMessage = (TextMessage) message;
            String text = textMessage.getText();
            Repository viewRepository = MongoDbRepository.getInstance();
            viewRepository.add(text);
            LOG.info("Received Event: "+ text);
        } catch (JMSException e) {
            LOG.error("Error: ", e);
        }
    }

}