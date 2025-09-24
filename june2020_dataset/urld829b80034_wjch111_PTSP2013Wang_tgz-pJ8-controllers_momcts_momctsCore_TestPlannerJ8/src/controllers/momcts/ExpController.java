package controllers.momcts;

import java.util.Random;


import framework.core.Controller;
import framework.core.Game;

public class ExpController extends Controller {
	int i;

	public ExpController(Game a_game, long a_timeDue){
		i=0;
	}
	
	public int getAction(Game a_game, long a_timeDue) {
		i++;
		return 3+i%2;
	}

}
