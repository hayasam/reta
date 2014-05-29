// Package : com.ben12.reta.view.buffering
// File : Buffering.java
// 
// Copyright (C) 2014 Beno�t Moreau (ben.12)
//
// This file is part of RETA (Requirement Engineering Traceability Analysis).
//
// RETA is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// RETA is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with RETA.  If not, see <http://www.gnu.org/licenses/>.
package com.ben12.reta.view.buffering;

import javafx.beans.property.BooleanProperty;

/**
 * @author Beno�t Moreau (ben.12)
 */
public interface Buffering<T>
{
	T getSubject();

	public boolean isEqualsBuffering();

	public void setEqualsBuffering(boolean equalsBuffering);

	BooleanProperty bufferingProperty();

	default boolean isBuffering()
	{
		return bufferingProperty().get();
	}

	void commit();

	void revert();
}
