
package com.source.consumer;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Consumer extends HttpServlet {

     @Resource(lookup = "jms/ConnectionFactoryTwo")
    private ConnectionFactory connectionFactory;
    @Resource(lookup = "jms/PersistentConnection")
    private Topic topic;
    public String RecieveMessage()
    throws JMSException{
        String msg="";
    Connection  connection =connectionFactory.createConnection();
    connection.start();
   // String subName="Sam";
    Session session=connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
      MessageConsumer consumer =session.createConsumer(topic);
    //MessageConsumer topicSubscriber=session.createDurableSubscriber(topic, subName);
     Message m =consumer.receive();
     
     if(m instanceof TextMessage){
         TextMessage message =(TextMessage)m;
          msg =message.getText();
         
     }
      
      
      connection.close();
      //topicSubscriber.close();
   
  return msg;
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws IOException,ServletException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try  {
           
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Consumer</title>");            
            out.println("</head>");
            out.println("<body>");
            
               String RecievedMessage= RecieveMessage();
            
            out.println(RecievedMessage);
            
            out.println("</body>");
            out.println("</html>");
        }catch(JMSException e){ 
            
        }
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

   
    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
