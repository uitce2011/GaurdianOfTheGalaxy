package uit.guardianofthegalaxy;

public class QuyDaoBay {
	float x;
	float y;

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public void setXY(float x, float y) {
		this.x = x;
		this.y = y;
	}

	/*
	 * 0: c�ch bay 1
	 * 1: c�ch bay 2
	 */
	public void moveDirection( int direction)
	{
		switch (direction) {
		case 0:  //C�ch bay 1
						y=y;
			break;
		case 1:  
					y=x/2;
			break;
		case 2:  
					y=x;	
			break;
		default:
			break;
		}
	}

}
