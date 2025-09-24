package com.mpos.lottery.te.thirdpartyservice.amqp.protobuf;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mpos.lottery.te.thirdpartyservice.amqp.RabbitMessagePublishMain;
import com.mpos.lottery.te.thirdpartyservice.amqp.TeTransactionMessage;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.MessageProperties;

public class CancellationMessagePulisher {
    private static Log logger = LogFactory.getLog(CancellationMessagePulisher.class);

    public static void main(String args[]) throws Exception {
        RabbitMessagePublishMain main = new RabbitMessagePublishMain("192.168.2.158", "/te", 5672, "amqp",
                "amqp");

        // assemble SALE message
        TeTransactionMessage.Cancellation.Builder cancellationBuilder = TeTransactionMessage.Cancellation
                .newBuilder();
        TeTransactionMessage.Transaction.Builder origTransBuilder = TeTransactionMessage.Transaction
                .newBuilder();
        TeTransactionMessage.Transaction origTrans = origTransBuilder.setId("orig-trans").setDevId("111")
                .setMerchantId("111").setOperatorId("OPERATOR-111").setCreateTime(new Date().getTime())
                .setTransType(206).build();

        TeTransactionMessage.Transaction.Builder cancelTransBuilder = TeTransactionMessage.Transaction
                .newBuilder();
        TeTransactionMessage.Transaction cancelTrans = cancelTransBuilder.mergeFrom(origTrans)
                .setId("cancel-trans").build();

        TeTransactionMessage.Cancellation cancelMsg = cancellationBuilder.setOrigTrans(origTrans)
                .setCancelTrans(cancelTrans).build();

        AMQP.BasicProperties props = MessageProperties.PERSISTENT_BASIC;
        Map<String, Object> headers = new HashMap<String, Object>();
        headers.put("TRANSACTION_ID", cancelTrans.getId());
        props.setHeaders(headers);
        main.publish("TE.200", "TE.CANCEL.200.1", props, cancelMsg.toByteArray());
        logger.debug(" [x] Published message successfully.");
    }
}
