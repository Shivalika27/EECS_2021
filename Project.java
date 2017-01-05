// do bne and beq
// recheck whole thing



//package MipsSimulator;

import java.io.*;
import java.util.*;

public class Project
{
	public static void main(String[] args)
	{
		// Step1: Read from the input file
		Scanner input = new Scanner(System.in);
		PrintStream output = new PrintStream(System.out);
		String memoryAddress = " ";
		String instruction = " ";
		
		int finalResult[] = new int[32];
		
		output.println("Enter file name: ");
		String fileName = input.next();
		File file = new File(fileName);
		
		try
		{
			
			// read from input file
			Scanner fileData = new Scanner(file);
			
			while(fileData.hasNext())
			{
			String[] split = fileData.nextLine().split(" ");
			
			memoryAddress = split[0];
			//output.println("memoryAddress "+memoryAddress);
			instruction = split[1];
			//output.println("instruction "+instruction);
			
			
						
			String instructionBinaryString = hexToBin(instruction);
			//output.println(instructionBinaryString);
			
			
			// Step3: Read first 6 bits of string
			//System.out.println(instructionBinaryString);
			String firstSixBits = instructionBinaryString.substring(0,6);
			//output.println(firstSixBits);
			//output.println();
			
			String operand;
			if (firstSixBits.equals("000000"))
			{
				// R - format
				//output.println("hey");
				operand = instructionBinaryString.substring(26,32);
			}
			
			else
			{
				// I - format
				//output.println("hello");
				operand = firstSixBits;
			}
			
			//output.println(operand);
			
			// Step4: Find which instruction
			String opInstruction = instructionFinder(operand);
			//output.println(opInstruction);
			
			int rsRDecimal = 0, rtRDecimal = 0, rdRDecimal = 0, shiftDecimal = 0, functDecimal = 0;
			int rsIDecimal = 0, rtIDecimal = 0, constDecimal = 0; 
			if (opInstruction.equals("sll") || opInstruction.equals("srl") || opInstruction.equals("add") || opInstruction.equals("and") || opInstruction.equals("nor") || opInstruction.equals("slt") || opInstruction.equals("sltu"))
			{
				//output.println("hey");
				// R - format
				String rsR = instructionBinaryString.substring(6,11);
				//output.println(rs);
				
				String rtR = instructionBinaryString.substring(11,16);
				//output.println(rt);
				
				String rdR = instructionBinaryString.substring(16,21);
				//output.println(rd);
				
				String shiftR = instructionBinaryString.substring(21,26);
				
				String functR = instructionBinaryString.substring(26,32);
				
				rsRDecimal = Integer.parseInt(rsR,2);
				//output.println(rsDecimal);
				
				rtRDecimal = Integer.parseInt(rtR,2);
				//output.println(rtDecimal);
				
				rdRDecimal = Integer.parseInt(rdR,2);
				//output.println(rdDecimal);
				
				shiftDecimal = Integer.parseInt(shiftR,2);
				
				functDecimal = Integer.parseInt(functR,2);
			}
			
			
			else if (opInstruction.equals("addi") || opInstruction.equals("bne") || opInstruction.equals("beq"))
			{
				//output.println("hello");
				// I - format
				String rsI = instructionBinaryString.substring(6,11);
				//output.println(rs);
				
				String rtI = instructionBinaryString.substring(11,16);
				//output.println(rt);
				
				String constant = instructionBinaryString.substring(16,32);
				//output.println(rd);
				
				rsIDecimal = Integer.parseInt(rsI,2);
				//output.println(rsDecimal);
				
				rtIDecimal = Integer.parseInt(rtI,2);
				//output.println(rtDecimal);
				
				constDecimal = Integer.parseInt(constant,2);
				//output.println(rdDecimal);
			}
			
			//output.println("rsIDecimal "+rsIDecimal);
			//output.println("rtIDecimal "+rtIDecimal);
			//output.println("constDecimal "+constDecimal);
			
			
			// Step7: Make an array for storing final result
			
			
			// Step8 : perform the instructions
			if (opInstruction.equals("sll"))
			{
				//output.println("sll");
				finalResult[rdRDecimal] = finalResult[rsRDecimal] << finalResult[shiftDecimal];
			}
			
			else if (opInstruction.equals("srl"))
			{
				//output.println("srl");
				finalResult[rdRDecimal] = finalResult[rsRDecimal] >>> finalResult[shiftDecimal];
			}
			
			else if (opInstruction.equals("addi"))
			{
				//output.println("addi");
				finalResult[rtIDecimal] = finalResult[rsIDecimal] + constDecimal;
				//output.println(finalResult[rtIDecimal]);
			}
			
			else if (opInstruction.equals("add"))
			{
				//output.println("add");
				finalResult[rtRDecimal] = finalResult[rsRDecimal] + finalResult[rtRDecimal];
			}
			
			else if (opInstruction.equals("and"))
			{
				//output.println("and");
				finalResult[rdRDecimal] = finalResult[rsRDecimal] & finalResult[rtRDecimal];
			}
			
			else if (opInstruction.equals("slt"))
			{
				//output.println("slt");
				if (finalResult[rsRDecimal] < finalResult[rtRDecimal])
				{
					finalResult[rsRDecimal] = 1;
				}
				
				else
				{
					finalResult[rsRDecimal] = 0;
				}
			}
			
			else if (opInstruction.equals("sltu"))
			{
				//output.println("sltu");
				if (Math.abs(finalResult[rsRDecimal]) < Math.abs(finalResult[rtRDecimal]))
				{
					finalResult[rsRDecimal] = 1;
				}
				
				else
				{
					finalResult[rsRDecimal] = 0;
				}
			}
			
			else if (opInstruction.equals("beq"))
			{
				//output.println("beq");
			}
			
			else if (opInstruction.equals("bne"))
			{
				//output.println("bne");
			}
			
			else if (opInstruction.equals("nor"))
			{
				//output.println("nor");
				finalResult[rdRDecimal] = ~(finalResult[rsRDecimal] | finalResult[rtRDecimal]);
			}
			
			/*for (int i = 0; i < finalResult.length; i ++)
			{
				output.println("[" + i + "]"+ " " + finalResult[i]);
				
			}		
			output.println();*/
			}
			fileData.close();
		}
		
		catch(FileNotFoundException e)
		{
			output.println(e);
		}
		
		// print the final array
		for (int i = 0;  i < 8; i = i + 4)
		{
			System.out.printf("[%d]%d	[%d]%d	[%d]%d	[%d]%d	\n",
					i, finalResult[i+1], i+1, finalResult[i+1],
					i+2,finalResult[i+2], i+3, finalResult[i+3]);
		}
		
		System.out.printf("[%d]%d	[%d]%d	[%d]%d	[%d]%d	\n",
				8, finalResult[8], 9, finalResult[9],
				10, finalResult[10], 11, finalResult[11]);
		
		
		for (int i = 12;  i < 32; i = i + 4)
		{
			System.out.printf("[%d]%d	[%d]%d	[%d]%d	[%d]%d	\n",
					i, finalResult[i], i+1, finalResult[i+1],
					i+2, finalResult[i+2], i+3, finalResult[i+3]);
		}
		
	}	// end of main

	// Step2: Convert hex into binary and save the string 
	private static String hexToBin(String hex)
	{
	    String bin = "";
	    String binFragment = "";
	    int iHex;
	    hex = hex.trim();
	    hex = hex.replaceFirst("0x", "");

	    for(int i = 0; i < hex.length(); i++)
	    {
	        iHex = Integer.parseInt(""+hex.charAt(i),16);
	        binFragment = Integer.toBinaryString(iHex);

	        while(binFragment.length() < 4)
	        {
	            binFragment = "0" + binFragment;
	        }
	        
	        bin += binFragment;
	    }
	    
	    return bin;
	}
	
	private static String instructionFinder(String op)
	{
		String instruction = "";
		
		if (op.equals("000000"))
		{
			instruction = "sll";
		}
		
		else if (op.equals("000010"))
		{
			instruction = "srl";
		}
		
		else if (op.equals("001000"))
		{
			instruction = "addi";
		}
		
		else if (op.equals("100000"))
		{
			instruction = "add";
		}
		
		else if (op.equals("100100"))
		{
			instruction = "and";
		}
		
		else if (op.equals("101010"))
		{
			instruction = "slt";
		}
		
		else if (op.equals("101011"))
		{
			instruction = "sltu";
		}
		
		else if (op.equals("0001000"))
		{
			instruction = "beq";
		}
		
		else if (op.equals("000101"))
		{
			instruction = "bne";
		}
		
		else if (op.equals("100111"))
		{
			instruction = "nor";
		}
		
		return instruction;
	}
		
}
