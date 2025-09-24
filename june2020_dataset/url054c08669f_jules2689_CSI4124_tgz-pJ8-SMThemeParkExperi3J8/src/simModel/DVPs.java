package simModel;

class DVPs {
	SMThemePark model; // for accessing the clock

	public DVPs(SMThemePark model) {
		this.model = model;
	}

	protected double uLoadUnloadTime() {
		if (model.boardingOption == Constants.DOUBLE_SIDED) {
			return 1.25; // double sided
		} else {
			return 2.0; // single sided
		}
	}

	protected double travelTime(int nextId) {
		switch (nextId) {
		case Constants.SH:
			return 5.0;
		case Constants.GI:
			return 8.0;
		case Constants.RC:
			return 7.0;
		default:
			return 6.0;
		}
	}

	protected double getPercentageOfCustomersLeaving(int fromStation, int toStation) {
		double result = 0;
		switch (fromStation) {
		case Constants.FP:
			switch (toStation) {
			case Constants.FP:
				result = 0;
				break;
			case Constants.GI:
				result = 0.35;
				break;
			case Constants.RC:
				result = 0.25;
				break;
			case Constants.SH:
				result = 0.4;
				break;
			default:
				break;
			}
			break;
		case Constants.GI:
			switch (toStation) {
			case Constants.FP:
				result = 0.42;
				break;
			case Constants.GI:
				result = 0;
				break;
			case Constants.RC:
				result = 0.29;
				break;
			case Constants.SH:
				result = 0.29;
				break;
			default:
				break;
			}
			break;
		case Constants.RC:
			switch (toStation) {
			case Constants.FP:
				result = 0.41;
				break;
			case Constants.GI:
				result = 0.31;
				break;
			case Constants.RC:
				result = 0;
				break;
			case Constants.SH:
				result = 0.28;
				break;
			default:
				break;
			}
			break;
		case Constants.SH:
			switch (toStation) {
			case Constants.FP:
				result = 0.37;
				break;
			case Constants.GI:
				result = 0.39;
				break;
			case Constants.RC:
				result = 0.24;
				break;
			case Constants.SH:
				result = 0;
				break;
			default:
				break;
			}
			break;
		default:
			break;
		}
		return result;
	}
}
