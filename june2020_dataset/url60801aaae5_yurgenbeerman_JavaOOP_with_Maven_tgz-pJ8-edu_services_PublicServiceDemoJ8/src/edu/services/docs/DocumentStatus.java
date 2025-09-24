package edu.services.docs;

import edu.utils.PublicRequestsUtils;

import java.util.ArrayList;
import java.util.GregorianCalendar;

/**
 * Created by yurii.pyvovarenko on 06.03.14.
 */
public class DocumentStatus {
    private DocumentLifecycle documentLifecycle;
    private ArrayList<String> documentStatusesHistory;
    private ArrayList<GregorianCalendar> documentStatusesDates;
    private int currentStatusIndex;
    private int previousStatusIndex;


    public DocumentStatus(DocumentLifecycle documentLifecycle) {
        if (null != documentLifecycle) {
            this.documentLifecycle = documentLifecycle;
            this.documentLifecycle.setInUse(true);
            currentStatusIndex = this.documentLifecycle.getStartStatusIndex();
            previousStatusIndex = this.documentLifecycle.getStartStatusIndex();
            documentStatusesHistory = new ArrayList<String>();
            documentStatusesDates = new ArrayList<GregorianCalendar>();
            addDocStatusesAndDatesHistory();
        }
    }

    public String getCurrentDocumentStatus() {
        return documentLifecycle.get(currentStatusIndex);
    }

    public GregorianCalendar getZeroStatusDate() {
        if (null != documentStatusesDates) {
            return documentStatusesDates.get(0);
        } else {
            return null;
        }
    }

    public void setNextDocumentStatus() {
        previousStatusIndex = currentStatusIndex;
        currentStatusIndex = documentLifecycle.getNextStatusIndex(currentStatusIndex);
        addDocStatusesAndDatesHistory();
    }

    public void setPreviousDocumentStatus() {
        int status = currentStatusIndex;
        currentStatusIndex = previousStatusIndex;
        previousStatusIndex = status;
        addDocStatusesAndDatesHistory();
    }

    public String getDocumentStatusesHistoryString() {
        String result = "";
        for (int i = 0; i < documentStatusesHistory.size(); i++) {
            result += "\n       Status '" + documentStatusesHistory.get(i) +
                    "' was assigned on " + PublicRequestsUtils.toTimeAndDateString(documentStatusesDates.get(i)) +
                    ". ";
        }
        return result;
    }

    private void addDocStatusesAndDatesHistory() {
        documentStatusesHistory.add(getCurrentDocumentStatus());
        documentStatusesDates.add(new GregorianCalendar());
    }

}
