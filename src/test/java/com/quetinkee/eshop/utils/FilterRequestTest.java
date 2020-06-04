package com.quetinkee.eshop.utils;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.quetinkee.eshop.model.enums.Size;
import com.quetinkee.eshop.model.projection.MinMaxPrice;

import org.junit.Test;

public class FilterRequestTest {
  
  @Test
  public void convertSizes_emptySizeSet_allFalseSizeArray() {
    FilterInfo info = new FilterInfo(new ArrayList<>(), new HashSet<>(), new HashSet<>(), new HashSet<>(), new MinMaxPrice());
    boolean[] sizes = info.getSizes();
    assertTrue(sizes.length == 3);
    assertTrue(sizes[0] == false);
    assertTrue(sizes[1] == false);
    assertTrue(sizes[2] == false);
  }

  @Test
  public void convertSizes_nonEmptySizeSet_sizeArrayCorrectlyFilled() {
    Set<Size> sizeSet = new HashSet<Size>();

    sizeSet.add(Size.LARGE);
    FilterInfo info = new FilterInfo(new ArrayList<>(), new HashSet<>(), new HashSet<>(), sizeSet, new MinMaxPrice());
    boolean[] sizes = info.getSizes();
    assertTrue(sizes.length == 3);
    assertTrue(sizes[0] == false);
    assertTrue(sizes[1] == false);
    assertTrue(sizes[2] == true);

    sizeSet.add(Size.SMALL);
    info = new FilterInfo(new ArrayList<>(), new HashSet<>(), new HashSet<>(), sizeSet, new MinMaxPrice());
    sizes = info.getSizes();
    assertTrue(sizes.length == 3);
    assertTrue(sizes[0] == true);
    assertTrue(sizes[1] == false);
    assertTrue(sizes[2] == true);

    sizeSet.remove(Size.LARGE);
    sizeSet.add(Size.MEDIUM);
    info = new FilterInfo(new ArrayList<>(), new HashSet<>(), new HashSet<>(), sizeSet, new MinMaxPrice());
    sizes = info.getSizes();
    assertTrue(sizes.length == 3);
    assertTrue(sizes[0] == true);
    assertTrue(sizes[1] == true);
    assertTrue(sizes[2] == false);
  }
}
