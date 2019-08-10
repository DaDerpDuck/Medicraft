package daderpduck.medicraft.capabilities;

public interface IBlood {
	void setBlood(float amount);
	float getBlood();

	void increaseBlood(float amount);
	void decreaseBlood(float amount);

	void setMaxBlood(float amount);
	float getMaxBlood();

	void setOxygen(double amount);
	double getOxygen();

	void increaseOxygen(double amount);
	void decreaseOxygen(double amount);

	double getMaxOxygen();
}
