package daderpduck.medicraft.capabilities;

public interface IBlood {
	void increase(float points);
	void decrease(float points);

	void setBlood(float points);
	float getBlood();
	float getMaxBlood();
}
