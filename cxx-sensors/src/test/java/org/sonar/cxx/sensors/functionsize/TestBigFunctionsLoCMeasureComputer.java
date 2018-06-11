/*
 * Sonar C++ Plugin (Community)
 * Copyright (C) 2010-2018 SonarOpenCommunity
 * http://github.com/SonarOpenCommunity/sonar-cxx
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package org.sonar.cxx.sensors.functionsize;

import org.junit.Before;
import org.junit.Test;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.sonar.api.ce.measure.Component;
import org.sonar.api.ce.measure.MeasureComputer;
import org.sonar.cxx.sensors.functioncomplexity.MeasureComputerTestHelper;

public class TestBigFunctionsLoCMeasureComputer {
  private BigFunctionsLoCMeasureComputer measureComputer;
    
  @Mock
  private MeasureComputer.MeasureComputerDefinitionContext definitionContext;
  
  @Mock
  private MeasureComputer.MeasureComputerDefinition.Builder definitionContextBuilder;
  
  @Mock
  private MeasureComputer.MeasureComputerContext context;
  
  @Before
  public void setUp(){
    MockitoAnnotations.initMocks(this);
    this.measureComputer = new BigFunctionsLoCMeasureComputer();
    
    when(this.definitionContext.newDefinitionBuilder()).thenReturn(definitionContextBuilder);
    when(this.definitionContextBuilder.setOutputMetrics(anyString())).thenReturn(this.definitionContextBuilder);
  }
  
  @Test
  public void testWhenDefiningShouldReturnCorrectDefinition(){
    this.measureComputer.define(definitionContext);
    verify(this.definitionContextBuilder).setOutputMetrics(FunctionSizeMetrics.BIG_FUNCTIONS_LOC.key());
    verify(this.definitionContextBuilder).build();
  }
  
  @Test
  public void testWhenCallingComputeShouldComputeSumCorrectly(){        
    MeasureComputerTestHelper.setupComponentAndIntMeasure(context, Component.Type.FILE, 42, FunctionSizeMetrics.BIG_FUNCTIONS_LOC.key());    
    this.measureComputer.compute(context);
    
    MeasureComputerTestHelper.setupComponentAndIntMeasure(context, Component.Type.FILE, 13, FunctionSizeMetrics.BIG_FUNCTIONS_LOC.key());    
    this.measureComputer.compute(context);
    
    MeasureComputerTestHelper.setupProject(context);
    this.measureComputer.compute(context);
    
    verify(context).addMeasure(FunctionSizeMetrics.BIG_FUNCTIONS_LOC.key(), 55);
  } 
  
  @Test
  public void testWhenCallingComputeWithUnexpectedComponentShouldNotAddMeasure(){
    MeasureComputerTestHelper.setupView(context);
    
    this.measureComputer.compute(context);
    verify(context, times(0)).addMeasure(anyString(), anyInt());
  }  

  
  @Test
  public void testWhenCallingComputeWithFileWithNoMeasureShouldNotThrow(){
    MeasureComputerTestHelper.setupComponentAndIntMeasure(context, Component.Type.FILE, 42, FunctionSizeMetrics.BIG_FUNCTIONS_LOC.key());    
    when(this.context.getMeasure(FunctionSizeMetrics.BIG_FUNCTIONS_LOC.key())).thenReturn(null);
    this.measureComputer.compute(context);
    
    verify(context, times(0)).addMeasure(anyString(), anyInt());
  }
    

}
