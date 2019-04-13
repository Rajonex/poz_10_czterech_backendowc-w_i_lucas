import morfeusz2
import json
from flask import Flask
from flask import request
from flask import *

def tokenize_and_lemmatize(text):
    return_word_list = []
    next_word = 0
    try:
        for list_of_tuples in morfeusz2.Morfeusz().analyse(str(text)):
            
            morf_actual_word = list_of_tuples[0]

            if next_word > morf_actual_word:
                continue

            next_word = list_of_tuples[1]
            analyse_tuple = list_of_tuples[2]
            return_word_list.append( (str(analyse_tuple[1])).lower())
    except:
        print("Error:", text)
        
    return return_word_list


title = "bardzo ładna czerwona sukienka rozmiar 32"
alle_title = "telefon iphone czerwony 32GB"

def get_jaccard(title, alle_title):
    title_word_list = tokenize_and_lemmatize(title)
    alle_title_word_list = tokenize_and_lemmatize(alle_title)

    set_title_word_list = set(title_word_list)
    set_alle_title_word_list = set(alle_title_word_list)

    intersection = len(set_title_word_list.intersection(set_alle_title_word_list))
    union = (len(set_title_word_list) + len(set_alle_title_word_list)) - intersection
    if union>0:
        jaccard = intersection/union
    
    return jaccard


app = Flask(__name__)


@app.route('/api/filter', methods=['POST'])
def get_result_json_objects():
    json_objects = request.data
    json_objects_dict = json.loads(json_objects)

    result_json_objects_data = {}
    result_json_objects_array = []
    for item in json_objects_dict["data"]:
        result_json_objects = {}
        checked_items = []
        key = item["key"]
        value = item["value"]
        base_name = key.get("name")
        jaccard_values = {}
        for item_val in value:
            item_name = item_val.get("name")
            jaccard_result = get_jaccard(base_name, item_name)
            if jaccard_result in jaccard_values:
                jaccard_values[jaccard_result].append(item_val)
            else:   
                temp_item_array = []
                temp_item_array.append(item_val)
                jaccard_values[jaccard_result] = temp_item_array

            if jaccard_result > 0.3:
                checked_items.append(item_val)
                jaccard_values[0.0] = jaccard_values.pop(jaccard_result)
        while(len(checked_items)<5):
            for i in range(len(jaccard_values[max(jaccard_values, key=float)])):
                checked_items.append(jaccard_values[max(jaccard_values, key=float)][i])
            jaccard_values[0.0] = jaccard_values.pop(max(jaccard_values))
        result_json_objects["key"] = item["key"]
        result_json_objects["value"] = checked_items
        result_json_objects_array.append(result_json_objects)
    result_json_objects_data["data"] = result_json_objects_array


    return Response(json.dumps(result_json_objects_data), mimetype='application/json')


if __name__ == '__main__':
      app.run(host='127.0.0.1', port=9000)