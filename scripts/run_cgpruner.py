import os

BENCHMARKS_FOLDER = "../../datasets/june2020_dataset"
RESULTS_FOLDER = "cgpruner_results"
FILE_WITH_MAIN_CLASS = "info/mainclassname"
SKIP_COMPLETED = True #skips if the output file is already there.
RUN_WALA_SCRIPT = "run_wala.py" 
CGPRUNER_FOLDER = "tools/cgpruner/code/run-on-single-program"
RUN_CGPRUNER_SCRIPT = "run_cg_pruner.py"
WALA_CG_FILE = "wala_cg.csv"
CUTOFF_PROBABILITY = "0.45"
CLASSIFIER = "tools/cgpruner/wala.joblib"

#create the output folder if it doesn't exist
results_folder_path = os.path.join(os.getcwd(),RESULTS_FOLDER)
if not os.path.exists(results_folder_path):
    os.mkdir(results_folder_path)

benchmarks_folder_path = os.path.join(os.getcwd(),BENCHMARKS_FOLDER)
classifier_path = os.path.join(os.getcwd(),CLASSIFIER)

# Run on all the benchmarks
print("Completed Benchmarks")
for benchmark in os.listdir(benchmarks_folder_path):
    print(benchmark)
    if (SKIP_COMPLETED):
        if os.path.exists(os.path.join(results_folder_path,(benchmark+".txt"))):
            print("skipping completed benchmark.")
            continue
    benchmark_path = os.path.join(benchmarks_folder_path,benchmark)
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

    #get result file
    result_path = os.path.join(results_folder_path,benchmark+".csv")
    cd_command = f'cd {CGPRUNER_FOLDER}'
    wala_command = f'python3 {RUN_WALA_SCRIPT} --input {jarfile_path} --output {WALA_CG_FILE} --main {mainclass_name}'
    cgpruner_command = f'python3 {RUN_CGPRUNER_SCRIPT} --output {result_path} --input {WALA_CG_FILE} --classifier {classifier_path} --cutoff {CUTOFF_PROBABILITY}'

    combined_command = cd_command + " && " + wala_command + " && " + cgpruner_command
    #print(combined_command)
    os.system(combined_command)
