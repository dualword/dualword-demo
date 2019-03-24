
#include <iostream>
#include <string>
#include <vector>

#include "CifFile.h"
#include "CifParserBase.h"
#include "ISTable.h"

using namespace std;

int main(int argc, char *argv[]) {
	    string cifFileName = "2rh1.cif";
	    string diagnostics;

	    CifFile *cifFileP = new CifFile;
	    CifParser *cifParserP = new CifParser(cifFileP);
	    cifParserP->Parse(cifFileName, diagnostics);
	    delete cifParserP;
	    if (!diagnostics.empty()) {
	        cout << "Diagnostics: " << endl << diagnostics << endl;
	    }

	    string firstBlockName = cifFileP->GetFirstBlockName();
	    cout << "firstBlockName:" << firstBlockName << endl;
	    Block &block = cifFileP->GetBlock(firstBlockName);
	    ISTable& atom_site = block.GetTable("atom_site");
	    cout << "atoms:" << atom_site.GetNumRows() << endl;
	    delete cifFileP;

}
