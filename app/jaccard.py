import morfeusz2
import json
from flask import Flask
from flask import request
from flask import *
from sklearn.metrics.pairwise import cosine_similarity
from scipy import spatial

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



def create_bag_of_words(json_objects):
    json_objects_dict = json.loads(json_objects)
    bag_of_words = set()
    for item in json_objects_dict["data"]:
        key = item["key"]
        value = item["value"]
        base_name = key.get("name")
        set_base_name = tokenize_and_lemmatize(base_name)
        for word in set_base_name:
            bag_of_words.add(word)
        for item_val in value:
            item_name = item_val.get("name")
            set_value_name = tokenize_and_lemmatize(item_name)
            for word in set_value_name:
                bag_of_words.add(word)

    return bag_of_words


def get_cosinus(bag_of_words, title, alle_title):

    title_word_list = tokenize_and_lemmatize(title)
    alle_title_word_list = tokenize_and_lemmatize(alle_title)

    title_vector = []
    alle_title_vector = []

    for word in bag_of_words:
        if word in title_word_list:
            title_vector.append(1)
        else:
            title_vector.append(0)

        if word in alle_title_word_list:
            alle_title_vector.append(1)
        else:
            alle_title_vector.append(0)

    # cosinus_measure = cosine_similarity([title_vector, alle_title_vector])
    cosine_similarity_value = 1 - spatial.distance.cosine(title_vector, alle_title_vector)

    return cosine_similarity_value



app = Flask(__name__)



@app.route('/api/filter', methods=['POST'])

def get_result_json_objects():
    json_objects = request.data
    
    algorithm = request.query_string.decode("utf-8").split("=")[1]
    threshold = 0.3
    bag_of_words = create_bag_of_words(json_objects)
    json_objects_dict = json.loads(json_objects)

    result_json_objects_data = {}
    result_json_objects_array = []
    for item in json_objects_dict["data"]:
        result_json_objects = {}
        checked_items = []
        key = item["key"]
        value = item["value"]
        base_name = key.get("name")
        similarity_method_values = {}
        for item_val in value:
            item_name = item_val.get("name")
            if(algorithm == "jaccard"):
                similarity_method_result = get_jaccard(base_name, item_name)
                threshold = 0.3
            else: 
                similarity_method_result = get_cosinus(bag_of_words, base_name, item_name)
                threshold = 0.6

            if similarity_method_result in similarity_method_values:
                similarity_method_values[similarity_method_result].append(item_val)
            else:   
                temp_item_array = []
                temp_item_array.append(item_val)
                similarity_method_values[similarity_method_result] = temp_item_array

            if similarity_method_result > threshold:
                checked_items.append(item_val)
                similarity_method_values[0.0] = similarity_method_values.pop(similarity_method_result)
        while(len(checked_items)<5):
            for i in range(len(similarity_method_values[max(similarity_method_values, key=float)])):
                checked_items.append(similarity_method_values[max(similarity_method_values, key=float)][i])
            similarity_method_values[0.0] = similarity_method_values.pop(max(similarity_method_values))
        result_json_objects["key"] = item["key"]
        result_json_objects["value"] = checked_items
        result_json_objects_array.append(result_json_objects)
    result_json_objects_data["data"] = result_json_objects_array


    return Response(json.dumps(result_json_objects_data), mimetype='application/json')


if __name__ == '__main__':
      app.run(host='127.0.0.1', port=9000)