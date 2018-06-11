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

import org.sonar.api.ce.measure.Component;
import org.sonar.api.ce.measure.Measure;
import org.sonar.api.ce.measure.MeasureComputer;
import org.sonar.api.measures.CoreMetrics;

public class BigFunctionsPercMeasureComputer implements MeasureComputer {

  @Override
  public MeasureComputerDefinition define(MeasureComputerDefinitionContext context) {
    return context.newDefinitionBuilder()
      .setInputMetrics(FunctionSizeMetrics.BIG_FUNCTIONS.key(), CoreMetrics.FUNCTIONS.key())
      .setOutputMetrics(FunctionSizeMetrics.BIG_FUNCTIONS_PERC.key())
      .build();        
  }
  
  private int totalBigFunctions = 0;
  private int totalFunctions = 0;

  @Override
  public void compute(MeasureComputerContext context) {
    if (context.getComponent().getType() == Component.Type.PROJECT){
      context.addMeasure(FunctionSizeMetrics.BIG_FUNCTIONS_PERC.key(), calculatePercentual(totalBigFunctions, totalFunctions));
    }
    else if (context.getComponent().getType() == Component.Type.FILE){
      Measure m = context.getMeasure(FunctionSizeMetrics.BIG_FUNCTIONS.key());
      if (m != null)
        totalBigFunctions += m.getIntValue();
      
      m = context.getMeasure(CoreMetrics.FUNCTIONS.key());
      if (m != null)
        totalFunctions += m.getIntValue();
    }    
  }
  
  private double calculatePercentual(int overThreshold, int total) {
    if (total > 0)
      return (((double)overThreshold * 100.0) / total);
    else 
      return 0;
  }
}
