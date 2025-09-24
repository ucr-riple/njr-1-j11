'''
This script runs soot on all the benchmarks.
Fill in the correct values for the macros at
the top of the file before executing.
'''
import os

SOOT_JAR = "tools/soot/sootclasses-trunk-jar-with-dependencies.jar"
DRIVER_PROGRAM = "tools/soot/driver/SootCallgraph.java"
SOOT_OPTIONS = "-pp -w -f n -app -p cg.spark on -allow-phantom-refs"
BENCHMARKS_FOLDER = "../final_dataset"
RESULTS_FOLDER = "soot_results"
JAVAC_COMMAND = "javac"
JAVA_COMMAND = "java"
FILE_WITH_MAIN_CLASS = "info/mainclassname"
SKIP_COMPLETED = True #skips if the output file is already there.
TIMEOUT = 1800
TIMEOUT_CMD = "timeout"

#create the output folder if it doesn't exist
results_folder_path = os.path.join(os.getcwd(),RESULTS_FOLDER)
if not os.path.exists(results_folder_path):
    os.mkdir(results_folder_path)

#add soot jar to classpath
classpath_command = "export CLASSPATH=" + SOOT_JAR + ":$CLASSPATH"

#compile the soot driver program. Ensure Java 8 is used.
compile_command = JAVAC_COMMAND + " " + DRIVER_PROGRAM

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

  #execute soot on the jar
  soot_command = (TIMEOUT_CMD 
    + " " + str(TIMEOUT)
    + " " + JAVA_COMMAND
    + " " + DRIVER_PROGRAM[:-5] #skip the .java part
    + " " + results_folder_path + "/" + benchmark + ".txt" #output
    + " " + mainclass_name
    + " -process-dir"
    + " " + jarfile_path
    + " " + SOOT_OPTIONS
  )

  #Execute all the commands. We do this together because they
  #need to be in the same 'process' shell. Else env. var. will be lost
  combined_command = (
    classpath_command
    + " && " + compile_command
    + " && " + soot_command
    )
  os.system(combined_command)
