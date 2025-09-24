'''
This script runs pmd on all the benchmarks.
Fill in the correct values for the macros at
the top of the file before executing.
'''
import os
import shutil

BENCHMARKS_FOLDER = "../final_dataset"
RESULTS_FOLDER = "pmd_results"
COMPILED_CLASSES_FOLDER = "pmd_classes"
SRC_FILES = "pmd_srcs.txt"
PMD_BINARY = "./tools/pmd-bin-6.32.0/bin/run.sh pmd"
PMD_RULESET_OPTION = "-R rulesets/java/quickstart.xml"
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
    pmd_input_option = f'-d {BENCHMARKS_FOLDER}/{benchmark}/src'

    #execute infer on the source files
    command = (TIMEOUT_CMD 
        + " " + str(TIMEOUT)
        + " " + PMD_BINARY
        + " " + PMD_RULESET_OPTION
        + " " + pmd_input_option
        + " > " +  RESULTS_FOLDER
        + "/" + benchmark + ".txt"
    )
    os.system(command)
