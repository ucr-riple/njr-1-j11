package Avatar;

import java.awt.event.*;
import java.util.*;

import javax.swing.JButton;

import AMath.Calc;
import GUI.*;
import Game.AGPmain;
import Ideology.*;
import Questing.Quest;
import Sentiens.Clan;

@SuppressWarnings({ "rawtypes", "unchecked", "serial" })
public class AvatarConsole extends APanel implements ActionListener {
	
	private Clan avatar;
	private final int DESWID = 200;
	private final int DESHGT = 270;
	private final int BUFFX = 5;
	private final int BUFFY = 5;
	private final int BUTTW = 150;
	private final int BUTTH = 20;
	private int numButtons = 0;

	private final String EDITAVATAR = "Edit Avatar";
	private final String POSSESS = "Possess Goblin";
	private final String AVATAR = "View Avatar";
	private final String NEWQUEST = "New Quest";
	private final String PURSUEQUEST = "Pursue Quest";
	private final String AUTOPILOTTOGGLE = "Autopilot Off";
	private final String STEPONCE = "Step Once";
	private final String PAUSEPLAY = "Play";
	private final String VIEWSPAWN = "View Spawn";
	
	private final SubjectiveComparator comparator;
	public final TreeSet choices;
	private final JButton pausePlayButton, autopilotButton;
	private final InputConsole inputConsole = new InputConsole();
	
	private AvatarConsole(GUImain P) {
		super(P);
		comparator = new SubjectiveComparator();
		choices = new TreeSet(comparator);
		setLayout(null);
		setButton(EDITAVATAR, -1);
		setButton(POSSESS, -1);
		setButton(AVATAR, KeyEvent.VK_A);
		setButton(NEWQUEST, KeyEvent.VK_Q);
		setButton(PURSUEQUEST, KeyEvent.VK_P);
		autopilotButton = setButton(AUTOPILOTTOGGLE, -1);
		setButton(STEPONCE, KeyEvent.VK_S);
		pausePlayButton = setButton(PAUSEPLAY, -1);
		setButton(VIEWSPAWN, -1);
		add(inputConsole);
		inputConsole.setBounds(BUFFX, BUFFY + numButtons * (BUTTH + BUFFY), BUTTW, BUTTH);
	}
	public static AvatarConsole create(GUImain P) {return new AvatarConsole(P);}
	public void setAvatar(Clan c) {avatar = c;}
	public Clan getAvatar() {return avatar;}
	public int getDesWid() {return DESWID;}
	public int getDesHgt() {return DESHGT;}
	
	private void showSpawn() {
		if (avatar == null) {return;}
		AGPmain.mainGUI.initializeSexDisplay();
	}

	public void showChoices(String prompt, Clan POV, Object[] choices, SubjectiveType sct,
			Calc.Listener listener, Calc.Transformer transformer) {
		AGPmain.pause();
		this.choices.clear();
		this.getComparator().setPOV(POV);
		switch (sct) {
		case ACT_PROFIT_ORDER: comparator.setComparator(comparator.ACT_PROFIT_ORDER); break;
		case RESPECT_ORDER: comparator.setComparator(comparator.RESPECT_ORDER); break;
		case VALUE_ORDER: comparator.setComparator(comparator.VALUE_ORDER); break;
		case QUEST_ORDER: comparator.setComparator(comparator.QUEST_ORDER); break;
		case NO_ORDER: comparator.setComparator(comparator.NO_ORDER); break;
		}
		for (Object choice : choices) {this.choices.add(choice);}
		APopupMenu.set(this, prompt, this.choices, listener, transformer);
	}
	public void showChoices(String prompt, Clan POV, Object[] choices,
			SubjectiveType sct, Calc.Listener listener) {
		showChoices(prompt, POV, choices, sct, listener, null);
	}
	public void showChoices(String prompt, Clan POV, Collection choices,
			SubjectiveType sct, Calc.Listener listener, Calc.Transformer transformer) {
		showChoices(prompt, POV, choices.toArray(), sct, listener, transformer);
	}
	public void showQuery(String prompt, String defaultContent, Calc.Listener listener) {
		AGPmain.pause();
		APopupQuery.set(this, prompt, defaultContent, listener);
	}
	
	private JButton setButton(String S, int KE) {
		JButton B = new JButton();
		B.setText(S);
		B.setActionCommand(S);
		B.setMnemonic(KE);
		B.addActionListener(this);
		add(B);
		B.setBounds(BUFFX, BUFFY + numButtons * (BUTTH + BUFFY), BUTTW, BUTTH);
		numButtons++;
		return B;
	}
	public SubjectiveComparator getComparator() {return ((SubjectiveComparator)choices.comparator());}

	private void newQuest() {
		this.showChoices("Choose new quest", avatar, Values.All, SubjectiveType.VALUE_ORDER, new Calc.Listener() {
			@Override
			public void call(Object arg) {
				avatar.MB.newQ(Quest.QtoQuest(avatar, ((Value) arg).pursuit()));
			}
		}, new Calc.Transformer<Value, String>() {
			@Override
			public String transform(Value v) {
				return Quest.QtoQuest(avatar, v.pursuit()).description();
			}
		});
	}
	
	public void avatarPursue() {
		if (avatar.MB.QuestStack.empty()) {this.newQuest();}
		else {
			avatar.MB.QuestStack.peek().avatarPursue();
			avatar.setActive(false);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (AVATAR.equals(e.getActionCommand())) {
			AGPmain.mainGUI.GM.loadClan(avatar);
		}
		else if (EDITAVATAR.equals(e.getActionCommand())) {
			AGPmain.mainGUI.initializeFaceEditor();
		}
		else if (POSSESS.equals(e.getActionCommand())) {
			setAvatar(AGPmain.mainGUI.GM.getClan());
		}
		else if (NEWQUEST.equals(e.getActionCommand())) {
			this.newQuest();
		}
		else if (PURSUEQUEST.equals(e.getActionCommand()) && avatar.isActive()) {
			avatarPursue();
		}
		else if ("Autopilot On".equals(e.getActionCommand())) {
			AGPmain.turnOffAutopilot();
			autopilotButton.setText("Autopilot Off");autopilotButton.setActionCommand("Autopilot Off");
		}
		else if ("Autopilot Off".equals(e.getActionCommand())) {
			AGPmain.turnOnAutopilot();
			autopilotButton.setText("Autopilot On");autopilotButton.setActionCommand("Autopilot On");
		}
		else if (STEPONCE.equals(e.getActionCommand())) {
			AGPmain.TheRealm.goOnce();
		}
		else if ("Play".equals(e.getActionCommand())) {
			AGPmain.play();
		}
		else if ("Pause".equals(e.getActionCommand())) {
			AGPmain.pause();
		}
		else if (VIEWSPAWN.equals(e.getActionCommand())) {
			showSpawn();
		}
		AGPmain.mainGUI.GM.setState();
		AGPmain.mainGUI.SM.setState();
	}

	public void showPlayButton() {pausePlayButton.setText("Play");pausePlayButton.setActionCommand("Play");}
	public void showPauseButton() {pausePlayButton.setText("Pause");pausePlayButton.setActionCommand("Pause");}

}
