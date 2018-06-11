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

public class BigFunctionsLoCMeasureComputer implements MeasureComputer {

  @Override
  public MeasureComputerDefinition define(MeasureComputerDefinitionContext context) {
    return context.newDefinitionBuilder()
      .setOutputMetrics(FunctionSizeMetrics.BIG_FUNCTIONS_LOC.key())
      .build();    
  }
  
  private int sum = 0;

  @Override
  public void compute(MeasureComputerContext context) {
    if (context.getComponent().getType() == Component.Type.PROJECT)
      context.addMeasure(FunctionSizeMetrics.BIG_FUNCTIONS_LOC.key(), sum);
    else if (context.getComponent().getType() == Component.Type.FILE){
      Measure m = context.getMeasure(FunctionSizeMetrics.BIG_FUNCTIONS_LOC.key());
      if (m != null)
        sum += m.getIntValue();
    }
  }
  
}