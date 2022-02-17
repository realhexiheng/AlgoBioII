#!/usr/bin/python
# center star alignment using Needleman-Wunsch algorithm
# author: Xiheng He
import numpy as np

GAP = 2
MATCH = 0
MISMATCH = 1

def score(a, b):
    if a == b:
        return MATCH
    elif a == '-' or b == '-':
        return GAP
    else:
        return MISMATCH

def matrix(seq1, seq2):
    F = np.zeros((len(seq1)+1, len(seq2)+1))
    for i in range(len(seq1)):
        F[i][0] = i * GAP
    for j in range(len(seq2)):
        F[0][j] = j * GAP
    for i in range(1, len(seq1)+1):
        for j in range(1, len(seq2)+1):
            match = F[i-1][j-1] + + score(seq1[i-1], seq2[j-1])
            delete = F[i-1][j] + GAP
            insert = F[i][j-1] + GAP
            F[i][j] = min(match, delete, insert)
    return F

def Needleman_Wunsch(seq1, seq2):
    F = matrix(seq1, seq2)
    Alignment1 = ""
    Alignment2 = ""
    i = len(seq1)
    j = len(seq2)
    while i > 0 or j > 0:
        score_current = F[i][j]
        if F[i][j] == F[i-1][j-1] + score(seq1[i-1], seq2[j-1]):
            Alignment1 += seq1[i-1]
            Alignment2 += seq2[j-1]
            i -= 1
            j -= 1
        elif score_current and F[i][j] == F[i-1][j] + GAP:
            Alignment1 += seq1[i-1]
            Alignment2 += "-"
            i -= 1
        elif score_current and F[i][j] == F[i][j-1] + GAP:
            Alignment1 += "-"
            Alignment2 += seq2[j-1]
            j -= 1
    
    while i > 0:
        Alignment1 += seq1[i-1]
        Alignment2 += "-"
        i -= 1
    while j > 0:
        Alignment1 += "-"
        Alignment2 += seq2[j-1]
        j -= 1
    return Alignment1[::-1], Alignment2[::-1]

def similarity(alignment1, alignment2):
    similarity = 0
    for a, b in zip(alignment1, alignment2):
        similarity += score(a, b)
    return similarity

def center_star(sequences):
    mutiple_alignments = ""
    score_all = np.zeros((len(sequences), len(sequences)))
    for i in range(len(sequences)):
        for j in range(len(sequences)):
            score_all[i][j] = similarity(sequences[i], sequences[j])
    sum_col = np.sum(score_all, axis=0)
    center_index = np.argmax(sum_col)
    for i in range(len(sequences)):
        if i != center_index:
            alignment1, alignment2 = Needleman_Wunsch(sequences[center_index], sequences[i])
            if mutiple_alignments == "":
                mutiple_alignments = alignment1 + "\n" + alignment2 + "\n"
            else:
                mutiple_alignments += alignment2 + "\n"
    return mutiple_alignments



# seq1 = "GATTACA"
# seq2 = "GTCGACGCA"
# alignment1, alignment2 = Needleman_Wunsch(seq1, seq2)
# print(similarity(alignment1, alignment2))

s1 = "QRCVIKYFAHMI"
s2 = "KYCDIFFYTIHM"
s3 = "KECQSLEKHM"
s4 = "EDCIFCVDCDVFIHE"
s5 = "PMCHGGCPQDHMQ"

print(center_star([s1, s2, s3, s4, s5]))