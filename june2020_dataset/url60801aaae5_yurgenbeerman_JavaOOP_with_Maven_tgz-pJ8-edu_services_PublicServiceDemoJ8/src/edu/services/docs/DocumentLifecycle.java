package edu.services.docs;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by yurii.pyvovarenko on 06.03.14.
 *
 * With subclasses of this class you may implement even non-linear document lifecycles.
 * Document lifecycle is a list of Statuses of a Document of some DocumentType with rules of rtanzition from one Status to another.
 * It is no allowed to modify DocumentLifecycle object when it is in use or is Finalised.
 */
public class DocumentLifecycle extends ArrayList<String> {
    private boolean isLifecycleInUse = false;
    private boolean isFinalized = false;
    private int startStatusIndex;
    private int finalStatusIndex;

    /* we can implement any logic on NextStatus determination
     * let's use simple linear statuses scheme
     */
    public DocumentLifecycle(String[] statuses) {
        for (String s : statuses){
            this.add(s);
        }
        setStartStatusIndex(0);
        setFinalStatusIndex(statuses.length - 1);
    }

    public int getNextStatusIndex(int currentStatusIndex) {
        if (finalStatusIndex != currentStatusIndex){
            return currentStatusIndex + 1;
        } else {
            return -1;
        }
    }

    public int getStartStatusIndex() {
        return startStatusIndex;
    }

    public void setStartStatusIndex(int startStatusIndex) {
        if ((!isLifecycleInUse) && (!isFinalized)) {
            this.startStatusIndex = startStatusIndex;
        } else {
            throw new IllegalStateException(DocDefaults.LIFECYCLE_IN_USE_OR_FINALIZED);
        }
    }

    public void setFinalStatusIndex(int finalStatusIndex) {
        if ((!isLifecycleInUse) && (!isFinalized)) {
            this.finalStatusIndex = finalStatusIndex;
        } else {
            throw new IllegalStateException(DocDefaults.LIFECYCLE_IN_USE_OR_FINALIZED);
        }
    }

    public boolean isInUse() {
        return isLifecycleInUse;
    }

    public void setInUse(boolean isListInUse) {
        this.isLifecycleInUse = isListInUse;
    }

    public boolean isFinalized() {
        return isFinalized;
    }

    public void setFinalized(boolean isFinalized) {
        this.isFinalized = isFinalized;
    }

    @Override
    public void trimToSize() {
        if ((!isLifecycleInUse) && (!isFinalized)) {
            super.trimToSize();
        } else {
            throw new IllegalStateException(DocDefaults.LIFECYCLE_IN_USE_OR_FINALIZED);
        }
    }

    @Override
    public String set(int index, String element) {
        if ((!isLifecycleInUse) && (!isFinalized)) {
            return super.set(index, element);
        } else {
            throw new IllegalStateException(DocDefaults.LIFECYCLE_IN_USE_OR_FINALIZED);
        }
    }

    @Override
    protected void removeRange(int fromIndex, int toIndex) {
        if ((!isLifecycleInUse) && (!isFinalized)) {
            super.removeRange(fromIndex, toIndex);
        } else {
            throw new IllegalStateException(DocDefaults.LIFECYCLE_IN_USE_OR_FINALIZED);
        }
    }

    @Override
    public void ensureCapacity(int minCapacity) {
        if ((!isLifecycleInUse) && (!isFinalized)) {
            super.ensureCapacity(minCapacity);
        } else {
            throw new IllegalStateException(DocDefaults.LIFECYCLE_IN_USE_OR_FINALIZED);
        }
    }

    @Override
    public boolean add(String s) {
        if ((!isLifecycleInUse) && (!isFinalized)) {
            return super.add(s);
        } else return false;
    }

    @Override
    public void add(int index, String element) {
        if ((!isLifecycleInUse) && (!isFinalized)) {
            super.add(index, element);
        }
    }

    @Override
    public String remove(int index) {
        if ((!isLifecycleInUse) && (!isFinalized)) {
            return super.remove(index);
        } else return null;
    }

    @Override
    public boolean remove(Object o) {
        if ((!isLifecycleInUse) && (!isFinalized)) {
            return super.remove(o);
        } else return false;
    }

    @Override
    public void clear() {
        if ((!isLifecycleInUse) && (!isFinalized)) {
            super.clear();
        }
    }

    @Override
    public boolean addAll(Collection<? extends String> c) {
        if ((!isLifecycleInUse) && (!isFinalized)) {
            return super.addAll(c);
        } else return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends String> c) {
        if ((!isLifecycleInUse) && (!isFinalized)) {
            return super.addAll(index, c);
        } else return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        if ((!isLifecycleInUse) && (!isFinalized)) {
            return super.removeAll(c);
        } else return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        if ((!isLifecycleInUse) && (!isFinalized)) {
            return super.retainAll(c);
        } else return false;
    }


}
