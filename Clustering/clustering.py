import json
import jieba.posseg as posseg
import numpy as np
import pandas as pd
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.cluster import KMeans


def jieba_cut(text):
    return [word for word in posseg.cut(text)
            if word.flag in
            ["n", "ns", "v", "vn", "nr", "nt", "ORG", "PER"]
            ]


if __name__ == "__main__":
    event_list_file = open("event_list.json", "r")
    event_list = json.load(event_list_file)
    event_list_file.close()

    vectorizer = TfidfVectorizer(tokenizer=jieba_cut, use_idf=True)
    X = vectorizer.fit_transform([event["title"] for event in event_list])

    n_clusters = 5
    model_kmeans = KMeans(n_clusters=n_clusters)
    model_kmeans.fit(X)

    cluster_labels = model_kmeans.labels_

    eventK_clusterV = {}
    clusterK_kwV_eventV = [[[], []] for i in range(n_clusters)]
    cluster_labels_list = cluster_labels.tolist()
    for i in range(len(event_list)):
        eventK_clusterV[event_list[i]["_id"]] = cluster_labels_list[i]
        clusterK_kwV_eventV[cluster_labels_list[i]][1].append(event_list[i]["_id"])
    file = open("eventK_clusterV.json", "w")
    json.dump(eventK_clusterV, file)
    file.close()

    word_vectors = vectorizer.get_feature_names()  # 词向量
    word_values = X.toarray()  # 向量值
    text_matrix = np.hstack((word_values, cluster_labels.reshape(word_values.shape[0], 1)))  # 将向量值和标签值合并为新的矩阵
    word_vectors.append("cluster_labels")  # 将新的聚类标签列表追加到词向量后面
    dataframe = pd.DataFrame(text_matrix, columns=word_vectors)  # 创建包含词向量和聚类标签的数据框
    dataframe.to_csv('text.csv')
    print(dataframe.head(1))  # 打印输出数据框第1条数据

    # 聚类结果分析

    word_weight = np.sum(dataframe.drop('cluster_labels', axis=1))

    for i in range(n_clusters):
        cluster = dataframe[dataframe['cluster_labels'] == i].drop('cluster_labels', axis=1)
        word_weight_cluster = np.sum(cluster, axis=0)
        word_weight_cluster += 10 * word_weight_cluster / word_weight

        clusterK_kwV_eventV[i][0] = [list(item)[0] for item in
                                     word_weight_cluster.sort_values(ascending=False)[:10].to_dict().keys()]
    file = open("clusterK_kwV_eventV.json", "w")
    json.dump(clusterK_kwV_eventV, file)
    file.close()
