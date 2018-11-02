package net.ntanet.client;

public class Dimension {
	
    public int width;
    
    public int height;
    
    public Dimension(Dimension d) {
        this(d.width, d.height);
    }

    public Dimension() {
        this(0, 0);
    }

    public Dimension(int width, int height) {
        setSize(width, height);
    }

	public double getWidth() {
		return width;
	}

	public double getHeight() {
		return height;
	}

	public Dimension getSize() {
		return new Dimension(width, height);
	}

	public void setSize(double width, double height) {
		setSize((int)Math.ceil(width), (int)Math.ceil(height));
	}

	public void setSize(Dimension d) {
		setSize(d.width, d.height);
	}

	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
	}

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof Dimension) {
            Dimension d = (Dimension)obj;
            return (d.width == width && d.height == height);
        }
        return false;
    }
    
    public String toString() {
        return getClass().getName() + "[width=" + width + ",height=" + height + "]";
    }
}
