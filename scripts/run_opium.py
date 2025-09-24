'''
This script runs opium on all the benchmarks.
Fill in the correct values for the macros at
the top of the file before executing.
'''
import os

BENCHMARKS_FOLDER = "../final_dataset"
OPIUM_JAR = "tools/opium/opium.jar"
RESULTS_FOLDER = "opium_results"
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
    
    #Get jar file
    jarfile = ''
    for file in os.listdir(f'{BENCHMARKS_FOLDER}/{benchmark}/jarfile'):
        if file.endswith(".jar"):
            jar_file = file
    jarfile_path = f'{BENCHMARKS_FOLDER}/{benchmark}/jarfile/{jar_file}'
    
    #execute spotbugs on the jar
    command = (TIMEOUT_CMD 
        + " " + str(TIMEOUT)
        + " " + "java -jar"
        + " " + OPIUM_JAR
        + " -cp " + jarfile_path
        + " > " +  RESULTS_FOLDER
        + "/" + benchmark + ".txt"
    )
    os.system(command)
