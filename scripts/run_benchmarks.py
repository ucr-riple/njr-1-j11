'''
This script executes all the benchmarks with no command line arguments.
Fill in the correct values for the macros at
the top of the file before executing.
'''
import os

BENCHMARKS_FOLDER =  "../../datasets/june2020_dataset"
RESULTS_FOLDER = "results/execution_results"
JAVA_COMMAND = "java"
TIMEOUT = 1800
TIMEOUT_CMD = "timeout"
FILE_WITH_MAIN_CLASS = "info/mainclassname"
SKIP_COMPLETED = True #skips if the output file is already there.

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

  #execute wala on the jar
  run_command = (TIMEOUT_CMD 
    + " " + str(TIMEOUT)
    + " " + JAVA_COMMAND
    + " -cp " + jarfile_path
    + " " + mainclass_name
    + " > " +  results_folder_path
    + "/" + benchmark + ".txt"
  )
  os.system(run_command)
