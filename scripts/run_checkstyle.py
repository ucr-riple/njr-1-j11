'''
This script runs checkstyle on all the benchmarks.
Fill in the correct values for the macros at
the top of the file before executing.
'''
import os
import shutil

BENCHMARKS_FOLDER = "../final_dataset"
RESULTS_FOLDER = "checkstyle_results"
SRC_FILES = "cs_srcs.txt"
CS_JAR = "tools/checkstyle/checkstyle-8.41-all.jar"
CS_OPTIONS = "-c /google_checks.xml"
SKIP_COMPLETED = True #skips if the output file is already there.
TIMEOUT = 1800
TIMEOUT_CMD = "timeout"

#create the output folder if it doesn't exist
if not os.path.exists(RESULTS_FOLDER):
    os.mkdir(RESULTS_FOLDER)

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
    
    #Get the location of the source file directory
    find_srcs_command = f'find {BENCHMARKS_FOLDER}/{benchmark}/src -name "*.java" > {SRC_FILES}'
    os.system(find_srcs_command)

    #execute infer on the source files
    command = (TIMEOUT_CMD 
        + " " + str(TIMEOUT)
        + " java -jar"
        + " " + CS_JAR
        + " " + CS_OPTIONS
        + " @" + SRC_FILES
        + " -o " +  RESULTS_FOLDER
        + "/" + benchmark + ".txt"
    )
    os.system(command)
