'''
This script runs spotbugs on all the benchmarks.
Fill in the correct values for the macros at
the top of the file before executing, but the
default values should work if all the instructions
were followed.
'''
import os

DOOP_FOLDER = "tools/doop"
DOOP_TEMP_RESULT = "tools/doop/last-analysis/Reachable.csv"
DOOP_COMMAND = "./doop -a context-insensitive" #--featherweight-anaysis option doesn't work.
BENCHMARKS_FOLDER = "big"
RESULTS_FOLDER = "doop_results"
FILE_WITH_MAIN_CLASS = "info/mainclassname"
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

    #get main class name
    mainclassname_file = os.path.join(benchmark_path,FILE_WITH_MAIN_CLASS)
    with open(mainclassname_file) as fp:
        mainclass_name = fp.read().splitlines()[0]

    #cd into doop folder and invoke doop
    command = ("cd" 
        + " " + DOOP_FOLDER
        + " " + "&&"
        + " " + TIMEOUT_CMD 
        + " " + str(TIMEOUT)
        + " " + DOOP_COMMAND 
        + " --main " + mainclass_name
        + " -i ../../" + jarfile_path 
    )
    os.system(command)

    #copy file from 'doop/last-analysis' to the RESULTS_FOLDER
    cp_command = ("cp" 
        + " " + DOOP_TEMP_RESULT
        + " " + results_folder_path
        + "/" + benchmark + ".txt"
        )
    os.system(cp_command)