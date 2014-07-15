// Package : com.ben12.reta
// File : Main.java
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

package com.ben12.reta;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author Beno�t Moreau (ben.12)
 */
public class Main extends Application
{
	/*
	 * (non-Javadoc)
	 * 
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public void start(Stage stage) throws Exception
	{
		try
		{
			FXMLLoader loader = new FXMLLoader();
			loader.setBuilderFactory(new JavaFXBuilderFactory());
			loader.setLocation(Main.class.getResource("view/MainConfigurationUI.fxml"));
			Parent root = (Parent) loader.load();

			stage.setScene(new Scene(root));
			stage.setTitle("RETA Configuration");
			stage.sizeToScene();
			stage.show();
		}
		catch (Exception e)
		{
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, "", e);
			System.exit(0);
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		try
		{
			LogManager.getLogManager().readConfiguration(
					ClassLoader.getSystemResourceAsStream("com/ben12/reta/resources/logging/logging.properties"));
		}
		catch (IOException e)
		{
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, "", e);
		}

		Application.launch(Main.class, args);
	}
}
