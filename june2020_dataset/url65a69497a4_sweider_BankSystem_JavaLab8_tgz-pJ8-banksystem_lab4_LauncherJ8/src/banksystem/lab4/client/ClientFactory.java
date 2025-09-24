/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package banksystem.lab4.client;

import banksystem.lab4.cashier.Cashier;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author alex
 */
public class ClientFactory {
    private final Collection<Integer> accounts;
    private final List<Cashier> cashiers;
    private int idCounter;
    

    public ClientFactory(Collection<Integer> accounts, List<Cashier> cashiers) {
        this.accounts = accounts;
        this.cashiers = cashiers;
        this.idCounter = 0;
    }
    
    public Client getNewClient(){
        int clientId = this.getNewClientId();
        int cashierId = clientId % (this.cashiers.size() - 1);
        return new Client(clientId, this.accounts, this.cashiers.get(cashierId));
    }

    private int getNewClientId() {
        return this.idCounter++;
    }
}
