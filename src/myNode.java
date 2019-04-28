import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class myNode {
	int nodeX;
	int nodeY;
	int distTrav;
	int ManDist;
	ArrayList<Integer> myPath = new ArrayList<Integer>();
	public int getNodeX() {
		return nodeX;
	}
	public void setNodeX(int nodeX) {
		this.nodeX = nodeX;
	}
	public int getManDist() {
		return ManDist;
	}
	public void setManDist(int ManDist) {
		this.ManDist = ManDist;
	}
	public int getNodeY() {
		return nodeY;
	}
	public void setNodeY(int nodeY) {
		this.nodeY = nodeY;
	}
	public int getDistTrav() {
		return distTrav;
	}
	public void setDistTrav(int distTrav) {
		this.distTrav = distTrav;
	}
	public ArrayList<Integer> getMyPath() {
		return myPath;
	}
	public void setMyPath(ArrayList<Integer> myPath) {
		this.myPath = myPath;
	}
	

	
	

}
