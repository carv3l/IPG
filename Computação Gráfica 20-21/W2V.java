package cglib2d.utils;

/**
 * 
 * @author ccarreto
 * Implementation of the Window to Viewport mapping.
 * 
 */


public class W2V {

	// Limits of the Window.
    int XWmin; 
	int YWmin;
	int XWmax;
	int YWmax;
	
	// Limits of the Viewport.
	int XVmin;
	int YVmin;
	int XVmax;
	int YVmax;
	
	// Scale factor for the Window to Viewport mapping.
	float F;

    // Default constructor.
	public W2V() {
		XWmin = 0;
		YWmin = 0;
		XWmax = 0;
		YWmax = 0;
		XVmin = 0;
		YVmin = 0;
		XVmax = 0;
		YVmax = 0;
		F = 1.0f;
	}
	
	// Constructor to define the limits of the Window and the Viewport.
	public W2V(int XWmin, int YWmin, int XWmax, int YWmax, int XVmin, int YVmin, int XVmax, int YVmax) {
     	this.XWmin = XWmin;
		this.YWmin = YWmin;
		this.XWmax = XWmax;
		this.YWmax = YWmax;
		this.XVmin = XVmin;
		this.YVmin = YVmin;
		this.XVmax = XVmax;
		this.YVmax = YVmax;
		setFScale();
	}

	// Method to define the limits of the Window.
	public void setWMaxMin(int XWmin, int YWmin, int XWmax, int YWmax) {
		this.XWmin = XWmin;
		this.YWmin = YWmin;
		this.XWmax = XWmax;
		this.YWmax = YWmax;	
	}

	// Method to define the limits of the Viewport.
	public void setVMaxMin(int XVmin, int YVmin, int XVmax, int YVmax) {
		this.XVmin = XVmin;
		this.YVmin = YVmin;
		this.XVmax = XVmax;
		this.YVmax = YVmax;
	}

	// Method to define the scale factor to use in Window to Viewport mapping.
	public void setFScale() {
		float Fx = (XVmax - XVmin) / (float) (XWmax - XWmin);
		float Fy = (YVmax - YVmin) / (float) (YWmax - YWmin);
		if (Fx < Fy)
			F = Fx;
		else
			F = Fy;
	}

	// Method to map X.
	public int MapX(int XW) {
		return (int) (F * (XW - XWmin) + XVmin);
	}
	
	public int MapX(double XW) {
		return (int) (F * (XW - XWmin) + XVmin);
	}

	//  Method to map Y.
	public int MapY(int YW) {
		return (int) (F * (YW - YWmin) + YVmin);
	}
	
	public int MapY(double YW) {
		return (int) (F * (YW - YWmin) + YVmin);
	}
}
