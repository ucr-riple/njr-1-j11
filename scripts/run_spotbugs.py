'''
This script runs spotbugs on all the benchmarks.
Fill in the correct values for the macros at
the top of the file before executing.
'''
import os

SPOTBUGS_EXECUTABLE = "tools/spotbugs/spotbugs-4.0.3/bin/spotbugs"
SPOTBUGS_COMMAND = "-effort:min fb analyze"
BENCHMARKS_FOLDER = "../final_dataset"
RESULTS_FOLDER = "spotbugs_results"
SKIP_COMPLETED = True #skips if the output file is already there.
TIMEOUT = 1800
TIMEOUT_CMD = "timeout"

#create the output folder if it doesn't exist
results_folder_path = os.path.join(os.getcwd(),RESULTS_FOLDER)
if not os.path.exists(results_folder_path):
    os.mkdir(results_folder_path)

#Loop through the benchmarks
print("Completed Benchmarks")
for benchmark in os.listdir(BENCHMARKS_FOLDER):
    print(benchmark)
    if (SKIP_COMPLETED):
        if os.path.exists(os.path.join(results_folder_path,(benchmark+".txt"))):
            print("skipping completed benchmark.")
            continue
    benchmark_path = os.path.join(BENCHMARKS_FOLDER,benchmark)
    #skip non-directories
    if not os.path.isdir(benchmark_path):
        continue
    #Get jar file
    jarfile = ''
    for file in os.listdir(os.path.join(benchmark_path,"jarfile")):
        if file.endswith(".jar"):
            jarfile = file
    jarfile_path = os.path.join(benchmark_path,("jarfile/" + jarfile))
    #execute spotbugs on the jar
    command = (TIMEOUT_CMD 
        + " " + str(TIMEOUT)
        + " " + SPOTBUGS_EXECUTABLE
        + " " + SPOTBUGS_COMMAND
        + " " + jarfile_path
        + " > " +  results_folder_path
        + "/" + benchmark + ".txt"
    )
    os.system(command)
