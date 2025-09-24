'''
This script runs spotbugs on all the benchmarks.
Fill in the correct values for the macros at
the top of the file before executing.
'''
import os

JRE = "/usr/lib/jvm/java-8-openjdk-amd64/jre" 
JREDUCE_OPTIONS = "--strategy classes"
BENCHMARKS_FOLDER = "../../datasets/june2020_dataset/"
RESULTS_FOLDER = "results/jreduce_results"
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
    
    #Create the core file (the set of classes to maintain)
    classes_file = os.path.join(benchmark_path,"info/classes")
    core_file = "jreduce_core.txt"
    with open(classes_file) as fr:
        with open(core_file, "a") as fw:
            lines = [line.rstrip() for line in fr]
            for line in lines:
                fw.write(line.replace(".","/"))
                fw.write("\n")


    #execute spotbugs on the jar
    command = (TIMEOUT_CMD 
        + " " + str(TIMEOUT)
        + " jreduce"
        + " --core @" + core_file
        + " --jre " + JRE
        + " " + JREDUCE_OPTIONS  
        + " -o " + RESULTS_FOLDER
        + "/" + benchmark + ".jar"
        + " " + jarfile_path
        + " -- ls"
    )
    os.system(command)

    #Remove the core file
    os.system("rm " + core_file)
