'''
This script runs the spoon framework on all the benchmarks.
Fill in the correct values for the macros at
the top of the file before executing.
'''
import os
import shutil

BENCHMARKS_FOLDER = "../final_dataset"
RESULTS_FOLDER = "spoon_results"
SPOON_JAR = "tools/spoon/spoon-core-8.4.0-beta-18-jar-with-dependencies.jar"
SPOON_FOLDER = "tools/spoon"
SPOON_LAUNCHER = "spoon.Launcher"
PROCESSOR_NAME = "CatchProcessor"
SKIP_COMPLETED = True #skips if the output file is already there.
TIMEOUT = 1800
TIMEOUT_CMD = "timeout"

#create the output folder if it doesn't exist
if not os.path.exists(RESULTS_FOLDER):
    os.mkdir(RESULTS_FOLDER)

#compile the processor
compile_command = f"javac -cp {SPOON_JAR} {SPOON_FOLDER}/{PROCESSOR_NAME}.java"
os.system(compile_command)

#Loop through the benchmarks
print("Completed Benchmarks")
for benchmark in os.listdir(BENCHMARKS_FOLDER):
    print(benchmark)
    if (SKIP_COMPLETED):
        if os.path.exists(f'{RESULTS_FOLDER}/{benchmark}.txt'):
            print("skipping completed benchmark.")
            continue
    #skip non-directories
    if not os.path.isdir(f'{BENCHMARKS_FOLDER}/{benchmark}'):
        continue
    
    src_folder = f'{BENCHMARKS_FOLDER}/{benchmark}/src'

    #execute infer on the source files
    command = (TIMEOUT_CMD 
        + " " + str(TIMEOUT)
        + " " + "java -cp"
        + " " + SPOON_FOLDER
        + ":" + SPOON_JAR
        + " " + SPOON_LAUNCHER
        + " -i " + src_folder
        + " > " +  RESULTS_FOLDER
        + "/" + benchmark + ".txt"
    )
    os.system(command)
