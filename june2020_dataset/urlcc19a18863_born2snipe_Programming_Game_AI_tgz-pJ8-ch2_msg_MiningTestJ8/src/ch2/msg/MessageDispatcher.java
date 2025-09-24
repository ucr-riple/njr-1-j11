package ch2.msg;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

class MessageDispatcher {

    private static final EntityManager ENTITY_MANAGER = EntityManager.instance();
    private static final MessageDispatcher instance = new MessageDispatcher();
    private LinkedList<Telegram> messages = new LinkedList();

    public static MessageDispatcher instance() {
        return instance;
    }

    public void discharge(BaseGameEntity receiver, Telegram telegram) {
        if (receiver != null) {
            receiver.handleMessage(telegram);
        }
    }

    public void dispatch(String sender, String receiver, MessageType type, double dispatchTime) {
        BaseGameEntity receiverEntity = ENTITY_MANAGER.get(receiver);

        //make sure the receiver is valid
        if (receiverEntity == null) {
            return;
        }

        Telegram telegram = new Telegram(sender, receiver, type, dispatchTime);

        //if there is no delay, route telegram immediately
        if (dispatchTime <= 0.0f) {
            //send the telegram to the recipient
            discharge(receiverEntity, telegram);
        } //else calculate the time when the telegram should be dispatched
        else {
            long currentTime = System.currentTimeMillis();

            telegram.dispatchTime = currentTime + dispatchTime;

            //and put it in the queue
            messages.add(telegram);
            Collections.sort(messages, new TelegramComparator());
        }
    }

    public void dispatchMessages() {
        //get current time
        long currentTime = System.currentTimeMillis();

        //now peek at the queue to see if any telegrams need dispatching.
        //remove all telegrams from the front of the queue that have gone
        //past their sell by date
        while (!messages.isEmpty() && messages.getFirst().dispatchTime < currentTime && messages.getFirst().dispatchTime > 0) {
            Telegram telegram = messages.getFirst();
            BaseGameEntity entity = ENTITY_MANAGER.get(telegram.receiver);
            discharge(entity, telegram);
            messages.remove(telegram);
        }

    }

    private static class TelegramComparator implements Comparator<Telegram> {

        @Override
        public int compare(Telegram o1, Telegram o2) {
            return Double.compare(o1.dispatchTime, o2.dispatchTime);
        }
    }
}
