import morfeusz2
import json
from flask import Flask
from flask import request
from flask import *
# import sys
# import settings


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

#title, alle_title = lista slow z tokenizacji i lematyzacji
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


# with open('example.json', encoding='utf-8') as f:
#     data = json.load(f)
app = Flask(__name__)


@app.route('/api/filter', methods=['POST'])
def get_result_json_objects():

    json_objects = request.data
    result_json_objects = {}
    json_objects_dict = json.loads(json_objects)

    # json_objects_dict = json.loads(json_objects)
    # json_objects_dict = {}
    # print(json_objects_dict["data"])
    for item in json_objects_dict["data"]:
        checked_items = []
        key = item["key"]
        value = item["value"]
        base_name = key.get("name")
        for item in value:
            item_name = item.get("name")
            jaccard_result = get_jaccard(base_name, item_name)
            print(jaccard_result)
            if jaccard_result > 0.3:
                checked_items.append(item)
        result_json_objects["key"] = checked_items

    print(result_json_objects)


    
    return Response(result_json_objects, mimetype='application/json')
    # base_name = json_objects_dict[0]


    # json_base = json_objects[0]

    # json_base_dict = json.loads(json_base)



#slownik z referencyjnym json i lista jsonow

json_base = '{ [{ "id" : 5,  "name" : "telefon iphone czerwony 32GB",  "price" : 5.55, "category" : "iphone" } : ["id" : 5,  "name" : "bardzo ładna czerwona sukienka rozmiar 32",  "price" : 5.55, "category" : "iphone"]] }'
# json_example2 = '{ "id" : 5,  "name" : "telefon iphone czerwony 32GB",  "price" : 5.55, "category" : "iphone" }'

example_json_list = {}

# get_result_json_objects(json_base)


# example_json_list["prod1"] = json_base
# example_json_list["prod1"] = json_example2

# get_result_json_objects(example_json_list)
# print(get_jaccard(title, alle_title))

if __name__ == '__main__':
      app.run(host='127.0.0.1', port=9000)
    #   reload(sys)
    #   sys.setdefaultencoding('utf-8')