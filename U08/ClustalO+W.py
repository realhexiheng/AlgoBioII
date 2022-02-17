#!/usr/bin/python
# ClustalO and ClustalW alignment
# author: Xiheng He

from Bio.SeqRecord import SeqRecord
from Bio.SeqIO import SeqIO
from Bio import Seq
from Bio.Alphabet import IUPAC
from Bio.Align.Applications import ClustalOmegaCommandline

sequence = {"Human": "TAVCMECFREAAYTKRLGTEKEVEVIGGADKYHSVCRLCYFKKA",
"Danio Rerio": "NAVCMQCFKEAAYTKRLGAEKEVEVIGGSDKYHAVCRCCY",
"Tryp Brucei": "SAVCMECHNRKASFTYRTVKSDERKLVGGSDMYMSVCRSCYETK",
"Mus Musc": "TAVCMECFREAAYTKRLGLEKEVEVIGGADKYHSVCRLCYFKKS",
"Vac Virus": "TAVCMKCFKEASFSKRLGEETEIEIIGGNDMYQSVCRKCY",
"Leishm Major": "TAVCMMCHEQPACFTRRTVNVEQQELIGGADMYIATCRECYSKQ",
"Thermot Marit": "AVCHRCGEYNATLTLKVAGGEEEIDVGGQEKYIAVCRDCY",
"Human M163": "TAVCMECFREAAYSKRLGTEKEVEVIGGADKYHSVCRLCYFKKA"}

records = (SeqRecord(Seq(seq, IUPAC.protein), name) for name,seq in enumerate(sequence) )
SeqIO.write(records, "input.fasta", "fasta")

clustalomega_cline = ClustalOmegaCommandline(infile="input.fasta", outfile="output.fasta", verbose=True, auto=True)