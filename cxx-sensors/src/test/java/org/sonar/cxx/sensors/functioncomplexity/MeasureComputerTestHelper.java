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
package org.sonar.cxx.sensors.functioncomplexity;

import static org.mockito.Mockito.*;
import org.sonar.api.ce.measure.Component;
import org.sonar.api.ce.measure.Measure;
import org.sonar.api.ce.measure.MeasureComputer;

public class MeasureComputerTestHelper {
  
  public static Component setupComponentAndIntMeasure(MeasureComputer.MeasureComputerContext context, 
    Component.Type type, int measure, String metricKey){
    
    Component component = mock(Component.class);
    when(component.getType()).thenReturn(type);
    
    Measure measureObj = mock(Measure.class);
    when(measureObj.getIntValue()).thenReturn(measure);
    
    when(context.getComponent()).thenReturn(component);
    when(context.getMeasure(metricKey)).thenReturn(measureObj);            
    
    return component;
  }
  
  public static void addIntMeasure(MeasureComputer.MeasureComputerContext context, 
          Component component, int measure, String metricKey){
    Measure measureObj = mock(Measure.class);
    when(measureObj.getIntValue()).thenReturn(measure);
    
    when(context.getMeasure(metricKey)).thenReturn(measureObj);
  }
  
  public static void setupProject(MeasureComputer.MeasureComputerContext context){
    Component component = mock(Component.class);
    when(component.getType()).thenReturn(Component.Type.PROJECT);
    when(context.getComponent()).thenReturn(component);
  }
  
  public static void setupView(MeasureComputer.MeasureComputerContext context){
    Component component = mock(Component.class);
    when(component.getType()).thenReturn(Component.Type.VIEW);
    when(context.getComponent()).thenReturn(component);
  }
  
}
