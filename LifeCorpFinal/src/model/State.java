package model;

public class State 
{
	String stateAbbrv;
	String stateName;
	
	public State()
	{
		
	}
	
	public State(String stateAbbrv, String stateName)
	{
		this.stateAbbrv = stateAbbrv;
		this.stateName = stateName;
	}

	public String getStateAbbrv() {
		return stateAbbrv;
	}

	public void setStateAbbrv(String stateAbbrv) {
		this.stateAbbrv = stateAbbrv;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stageName) {
		this.stateName = stageName;
	}
	
	
}
