package in.mustafaak.izuna.actions;

import in.mustafaak.izuna.meta.WaveEnemy;
import in.mustafaak.izuna.meta.WaveInfo;
import in.mustafaak.izuna.meta.WavePath;

import java.util.ArrayList;
import java.util.List;

public abstract class Action {
	public int endX;
	public int endY;

	public Action() {

	}

	protected ArrayList<Action> actions = new ArrayList<>();

	protected void addAction(Action... newActions) {
		for (Action i : newActions) {
			actions.add(i);
		}
	}

	public abstract ArrayList<WavePath> getPath(WavePath prev);

	public static ArrayList<WavePath> construct(WavePath last, Action... actions) {
		ArrayList<WavePath> paths = new ArrayList<>();
		for (int i = 0; i < actions.length; i++) {
			Action a = actions[i];
			if (a.actions.size() == 0) {
				// Primitive action
				paths.addAll(a.getPath(last));
			} else {
				Action[] children = a.actions.toArray(new Action[a.actions.size()]);
				paths.addAll(construct(last, children));
			}
			last = paths.get(paths.size() - 1);
		}

		return paths;
	}

	public static void wiggle(WaveInfo waveInfo, int dx, int dy, int duration) {
		for (WaveEnemy w : waveInfo.getEnemies()) {
			List<WavePath> p = w.getPaths();
			Loop loop = new Loop();
			Move move1 = new Move(dx, dy, duration / 2);
			Move move2 = new Move(-dx, -dy, duration / 2);
			List<WavePath> d = Action.construct(p.get(p.size() - 1), loop, move1, move2);
			p.addAll(d);
		}
	}
}
