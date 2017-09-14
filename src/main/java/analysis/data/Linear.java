package analysis.data;

import analysis.Triple;

import java.util.Iterator;
import java.util.List;

/**
 * analysis.data.Linear object to hold point data. This is abstract to allow for any type of List to be used as the
 * containing object (and other features that clients might extend)
 */
public abstract class Linear implements OperablePointContainer {


  protected List <Triple> points;

  public Linear(List <Triple> points) {
    this.points = points;
  }

  public abstract List <Triple> getData();

  @Override
  public Triple getMax() {
    int maxX = Integer.MIN_VALUE;
    int maxY = Integer.MIN_VALUE;
    int maxZ = Integer.MIN_VALUE;

    for (Triple pt : points) {
      maxX = Math.max(pt.getX(), maxX);
      maxY = Math.max(pt.getY(), maxY);
      maxZ = Math.max(pt.getZ(), maxZ);
    }

    if (maxX == Integer.MIN_VALUE) maxX = -1;
    if (maxY == Integer.MIN_VALUE) maxY = -1;
    if (maxZ == Integer.MIN_VALUE) maxZ = -1;

    return new Triple(maxX, maxY, maxZ);
  }


  public Triple getDimensions() {

    Triple mins = getMin();
    Triple maxes = getMax();

    return new Triple(maxes.getX()-mins.getX(), maxes.getY()-mins.getY(), maxes.getZ()-mins.getZ());
  }


  //returns a triplet containing the minimum x, y, z values in channel
  //if there are no points in the channel, will return a triple containing -1's
  public Triple getMin() {
    int minX = Integer.MAX_VALUE;
    int minY = Integer.MAX_VALUE;
    int minZ = Integer.MAX_VALUE;

    for (Triple pt : points) {
      minX = Math.min(pt.getX(), minX);
      minY = Math.min(pt.getY(), minY);
      minZ = Math.min(pt.getZ(), minZ);
    }

    if (minX == Integer.MAX_VALUE) minX = -1;
    if (minY == Integer.MAX_VALUE) minY = -1;
    if (minZ == Integer.MAX_VALUE) minZ = -1;

    return new Triple(minX, minY, minZ);
  }

  @Override
  public void makeRelative() {
    Triple mins = getMin();
    translate(-mins.getX(), -mins.getY(), -mins.getZ());
  }

  @Override
  public void translate(int xOffset, int yOffset, int zOffset) {
    for (Triple pt : points) {
      pt.setX(pt.getX() + xOffset);
      pt.setY(pt.getY() + yOffset);
      pt.setZ(pt.getZ() + zOffset);
    }
  }

  @Override
  public Iterator<Triple> iterator() {
    return points.iterator();
  }


  @Override
  public void add(Object element) {
    points.add((Triple) element);
  }


  @Override
  public Triple getCentroid() {
    int x = 0, y = 0, z = 0;
    for (Triple pt : points) {
      x += pt.getX();
      y += pt.getY();
      z += pt.getZ();
    }

    int numPts = points.size();

    return new Triple(x/numPts, y/numPts, z/numPts);
  }
}