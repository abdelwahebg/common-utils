/**
 * Project Name:commons
 * File Name:CommandUtils.java
 * Package Name:com.github.becausetesting.command
 * Date:Apr 16, 201610:59:06 PM
 * Copyright (c) 2016, alterhu2020@gmail.com All Rights Reserved.
 *
*/

package com.github.becausetesting.command;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.axis.utils.IOUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * ClassName:CommandUtils  
 * Function: TODO ADD FUNCTION.  
 * Reason:	 TODO ADD REASON.  
 * Date:     Apr 16, 2016 10:59:06 PM 
 * @author   Administrator
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
public class CommandUtils {

	
	/**
	 * not recommand
	 * @deprecated
	 * @Title: executeCommand
	 * @Description: TODO
	 * @author alterhu2020@gmail.com
	 * @param @param
	 *            command
	 * @param @return
	 * @return String return type
	 * @throws @example:
	 *             Runtime.getRuntime().exec(
	 *             "runas /profile /user:Administrator /savecred \"cmd.exe /c Powrprof.dll,SetSuspendState\""
	 *             );
	 */

	public static String executeCommand(String command) {

		StringBuffer output = new StringBuffer();

		Process p;
		try {

			p = Runtime.getRuntime().exec(command);

			p.waitFor();
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(p.getInputStream()));

			String line = "";
			while ((line = reader.readLine()) != null) {
				output.append(line + "\n");
			}
			// logger.info("Command output is:"+line);

		} catch (Exception e) {
			System.out.println("exception happened running command - here's what I know: ");
			e.printStackTrace();
			System.exit(-1);
		}

		return output.toString();

	}

	/**
	 * not recommand
	 * @deprecated using executeCommand(List<String>)
	 * @Title: executeCommand
	 * @Description: TODO
	 * @author alterhu2020@gmail.com
	 * @param @param
	 *            command
	 * @param @return
	 * @return String return type
	 * @throws @example:
	 *             Runtime.getRuntime().exec(
	 *             "runas /profile /user:Administrator /savecred \"cmd.exe /c Powrprof.dll,SetSuspendState\""
	 *             );
	 */

	public static String executeCommandwithAdminstrator(String command) {

		StringBuffer output = new StringBuffer();

		Process p;
		try {
			p = Runtime.getRuntime().exec(
					"cmd.exe /c echo password123 | runas /profile /user:Administrator /savecred \"" + command + "\"");
			p.waitFor();
			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

			String line = "";
			while ((line = reader.readLine()) != null) {
				output.append(line + "\n");
			}

		} catch (Exception e) {
			System.out.println("exception happened running command - here's what I know: ");
			e.printStackTrace();
			System.exit(-1);
		}

		return output.toString();

	}

	public static String executeCommand(List<String> command) {

		StringBuffer output = new StringBuffer();
		Process process = null;
		try {
			ProcessBuilder pb = new ProcessBuilder(command);

			process = pb.start();

			// process.waitFor();
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

			String line = "";
			while ((line = reader.readLine()) != null) {
				output.append(line + "\n");
				System.out.println(line);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			System.exit(-1);
		}
		return output.toString();
	}

	public static String executeCommand(String workdirectory, List<String> command) {

		StringBuffer output = new StringBuffer();
		Process process = null;
		try {
			ProcessBuilder pb = new ProcessBuilder(command);
			System.out.println("command input is:" + command.toString());
			pb.directory(new File(workdirectory));
			process = pb.start();

			// process.waitFor();
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

			String line = "";
			while ((line = reader.readLine()) != null) {
				output.append(line + "\n");
				System.out.println(line);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			System.exit(-1);
		}
		return output.toString();
	}

	public static String destoryWindowsProcess(String processname) {

		StringBuffer output = new StringBuffer();
		Process process = null;
		try {
			List<String> command = new ArrayList<String>();
			command.add("taskkill.exe");
			command.add("/F");
			command.add("/IM");
			command.add(processname);
			System.out.println("Destory Process Command>>> " + command.toString() + " !");
			ProcessBuilder pb = new ProcessBuilder(command);

			process = pb.start();
			Thread.sleep(1000);
			process.destroy();
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

			String line = "";
			while ((line = reader.readLine()) != null) {
				output.append(line + "\n");
				System.out.println(line);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("exception happened running command - here's what I know: ");
			e.printStackTrace();
			System.exit(-1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return output.toString();
	}

	/*
	 * apache command utility
	 */

	/**
	 * Execute a command on the operating system using Java's built-in Process
	 * class
	 *
	 * @param command
	 *            A string representation of the command to execute.
	 * @param getOutput
	 *            Whether or not to get the output/error streams of the process
	 *            you forked. This is helpful for debugging reasons.
	 * @return A string representation of output/error streams of the process.
	 */
	/*
	public static String executeCommandUsingJavaRuntime(String command, boolean getOutput) {
		return executeCommandUsingJavaRuntime(new String[] { command }, getOutput);
	}

	*/
	/**
	 * Execute a command on the operating system using Apache Commons Exec. This
	 * function runs asynchronously and dumps both stderr and stdout streams to
	 * a temp file.
	 *
	 * @param commandLine
	 *            The command to be executed.
	 * @param outputStreamHandler
	 *            An output stream to dump the process stderr and stdout to it.
	 */
	/*
	public static void executeCommandUsingApacheExec(CommandLine commandLine, OutputStream outputStreamHandler) {
		try {
			DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();
			PumpStreamHandler streamHandler = new PumpStreamHandler(outputStreamHandler);
			logger.info("commandLine: " + commandLine.toString());
			Executor process = new DefaultExecutor();
			process.setExitValue(0);
			process.setStreamHandler(streamHandler);
			process.execute(commandLine, resultHandler);

			// resultHandler.waitFor();
		} catch (Exception ex) {
			logger.error("An exception was thrown.", ex);
		}
	}
	*/

	

	/**
	 * Get the command in a string form.
	 *
	 * @param command
	 *            The command represented as a string array.
	 * @return A string representing the command.
	 */
	public static String convertCommandStringArrayToString(String[] command) {
		String output = "";
		for (String com : command) {
			output += " " + com;
		}

		return output;
	}
}
