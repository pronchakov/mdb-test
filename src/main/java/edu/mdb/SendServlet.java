package edu.mdb;

import javax.annotation.Resource;
import javax.jms.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@WebServlet(urlPatterns = "/send")
public class SendServlet  extends HttpServlet {

    @Resource(mappedName = "java:/ConnectionFactory")
    private ConnectionFactory connectionFactory;

    @Resource(mappedName = "java:/queue/test")
    private Queue queue;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        try {

            String name = req.getParameter("name");

            Session session = connectionFactory.createConnection().createSession();

            MessageProducer producer = session.createProducer(queue);

            TextMessage message = session.createTextMessage("Hello " + name);
            producer.send(message);

            producer.close();

            session.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }



    }
}
