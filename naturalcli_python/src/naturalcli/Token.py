"""
  <p>
  The <code>Token</code> class implements a token for the 
  command grammar.
  </p>
  
  @author Ferran Busquets
"""

class InvalidTokenError(Exception):
    def __init__(self, value):
        self.value = value
    def __str__(self):
        return repr(self.value)

class Token:
    
    CHAR_BEGIN_PARAM = '<' # Beginning char for a parameter
    CHAR_END_PARAM = '>' # Ending char for a parameter
    CHAR_BEGIN_OPT = '[' # Beginning char for an optional token
    CHAR_END_OPT= ']' # Ending char for an optional token 
    CHAR_NAME_TYPE = ':' # Char separator for a parameter name and type 
    VAR_ARGS = "..." # String for variable arguments token
    
    text = None # Texts giving sense to the token
    
    def __init__(self, text) :
        """
         * Constructor for the token
         * 
         * @param text the token text
         * @throws InvalidTokenError 
         * @throws InvalidTokenError 
        """
        setText(text)
    
    
    def getText(self) :
        """
         * Get the token text 
         * 
         * @return the token text 
        """
        return self.text
    


    def setText(self, text) :
        """
         * Set the token text and validate it
         * 
         * @param text the token text to set
         * @throws InvalidTokenError 
         * @throws InvalidTokenError 
        """
        validate(text)
        self.text = text
    
    
    def validate(self, text) :     
        # Validate None
        if text == None : 
            raise InvalidTokenError, "None token text"
        if text.length() == 0 : 
            raise InvalidTokenError, "Empty token text"
        # Validate invalid optional parameters
        bad_start = CHAR_BEGIN_PARAM+CHAR_BEGIN_OPT
        bad_end = CHAR_END_OPT+CHAR_END_PARAM
        opt_par_start = CHAR_BEGIN_OPT+CHAR_BEGIN_PARAM
        opt_par_end = CHAR_END_PARAM+CHAR_END_OPT
        if text.startswith(bad_start) or text.endswith(bad_end) :
            raise InvalidTokenError("Bad optional parameter token")
        if text.startswith(opt_par_start) and not text.endswith(opt_par_end) :
            raise InvalidTokenError("Bad optional parameter token")
        if not text.startswith(opt_par_start) and text.endswith(opt_par_end) :
            raise InvalidTokenError("Bad optional parameter token")
        # Validate the options format
        first = text[0]
        last = text[-1]
        if first == CHAR_BEGIN_OPT and last != CHAR_END_OPT :
            raise InvalidTokenError("Bad optional token")
        if first != CHAR_BEGIN_OPT and last == CHAR_END_OPT :
            raise InvalidTokenError("Bad optional token")
        # Validate the parameter format
        if first == CHAR_BEGIN_PARAM and last != CHAR_END_PARAM :
            raise InvalidTokenError("Bad parameter token")
        if first != CHAR_BEGIN_PARAM and last == CHAR_END_PARAM :
            raise InvalidTokenError("Bad parameter token")
    

    def isOptional(self) :
        """
         * Checks if it's an optional token
         * 
         * @return <code>true</code> if its optional, <code>False</code> otherwise
        """
        begin = text[0] == CHAR_BEGIN_OPT
        end = text[-1] == CHAR_END_OPT
        return begin and end    
    
    def isParameter(self) :
        """
         * Checks if it's a parameter token
         * 
         * @return <code>true</code> if it's a parameter, <code>False</code> otherwise
        """                
        if self.isOptional() :
          begin_i = 1
          end_i = 2
        else :
          begin_i = 0
          end_i = 1
        begin = text[begin_i] == CHAR_BEGIN_PARAM
        end = text[len(text)-end_i] == CHAR_END_PARAM
        return begin and end
    

    def isOptionalParameter(self) :
        """
         * Checks if it's an optional parameter token
         * 
         * @return <code>true</code> if its optional parameter, <code>False</code> otherwise
        """    
        return isParameter() and isOptional()
    
    
    def isMandatoryParameter(self) :
        """
         * Checks if it's a mandatory parameter token
         * 
         * @return <code>true</code> if it's mandatory parameter, <code>False</code> otherwise
        """    
        return isParameter() and not isOptional()
        
    
    def getParameterInfo(self, n1t2) :
        """
         * Helper method for @link org.naturalcli.Token#getParameterName() and
         * @link org.naturalcli.Token#getParameterName()
         * 
         * @param n1t2 the info requested. 1 for name and 2 for type
         * @return the parameter name, type name or <code>None</code>
        """    
        if n1t2 != 1 and n1t2 != 2 :
            raise RuntimeException("Bad value for n1t2.")
        if not self.isParameter() :
            return None
        word = self.getWord()
        i = word.find(CHAR_NAME_TYPE)
        if i == -1 :
            return word
        if n1t2 == 1 :
        	return word[0:i]
        else :
            return word[i+1]
    
        
    def getParameterName(self) :
        """
         * Gets the parameter name for the token
         * 
         * @return the parameter name, or <code>None</code> if it's not a parameter.
        """        
        return self.getParameterInfo(1)
        

    def getParameterTypeName(self) :
        """
         * Gets the parameter type name for the token
         * 
         * @return the parameter type name, or <code>None</code> if it's not a parameter.
        """           
        return self.getParameterInfo(2)
        
    
    def isIdentifier(self) :
        """
         * Determines if it's an identifier
         * 
         * @return <code>true</code> if it's an identifier, <code>False</code> otherwise
        """    
        return not self.isParameter()
    

    def isVarArgs(self) :
        """
         * Determines if it's a variable arguments token
         * 
         * @return <code>true</code> if it's a variable arguments token, <code>False</code> otherwise
        """    
        return self.text == VAR_ARGS;
        
    
    def getWord(self) :
        """
         * Obtains the word that represents the token, it means
         * the token text without optional or parameter chars
         * 
         * @return the word that represents the token
        """
        if isOptionalParameter() :
            return text[2:-2]
        elif isOptional() or isParameter() :
            return text[1:-1]
        return text
    
    
    def matches(self, t, pv) :
        """
         * Checks the text given to see if it's like the token
         * 
         * @param text the text to match
         * @param pv the parameter validator
         * @return <code>true</code> if matches, <code>False</code> if not
         * @throws UnknownParameterType 
         * @throws UnknownParameterType
        """
        if self.isIdentifier() :
            return self.getWord() == t
        elif self.isParameter() :
            return pv.validate(t, self.getParameterTypeName()) == None
        return False
    
    

