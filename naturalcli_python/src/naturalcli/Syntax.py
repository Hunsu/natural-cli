"""
  Implements a class with a syntax definition and parser.
  
  @author Ferran Busquets
 
"""

class InvalidSyntaxError(Exception):
    def __init__(self, value):
        self.value = value
    def __str__(self):
        return repr(self.value)


class Syntax:

    def __init__ (self, definition):
        """
         Constructor for the Syntax class
         
         @param definition the syntax definition
         @throws InvalidSyntaxException 
        """
        setDefinition(definition)

    def getDefinition(self):
        """
          Gets the syntax definition

          @return the definition
        """        
        return self.definition
    
    def setDefinition(self, definition):
        """
         Sets the definition for the syntax
          
         @param definition the definition to set
         @throws InvalidSyntaxException 
        """
        if definition == null or definition.length() == 0 :
            raise IllegalArgumentException, "The definition cannot be null or empty string."
        self.definition = definition
        compile()
        
    def compile(self):
        """
         Creates the grammar for the command
         
         @throws InvalidSyntaxException           
        """
        self.grammar = []
        tokens = definition.split(' ')
        last_t = None
        for i in range(len(tokens)-1) :
            s = tokens[i]
            t = None
            try :
                t = Token(s)
            except InvalidTokenException :
                raise InvalidSyntaxException, "Bad token."+e
            # Validating the variable argument token
            if t.isVarArgs() : 
                if last_t == None or i != len(tokens.length)-1 :
                    raise InvalidSyntaxException, "Variable arguments token only allowed at the end."
                if not last_t.isMandatoryParameter() :
                    raise InvalidSyntaxException, "Variable arguments have to follow a mandatory parameter."
            # Validating optional parameters
            if t.isParameter() and last_t != None and last_t.isOptional() \
               and t.getParameterTypeName() == last_t.getParameterTypeName() :
               raise InvalidSyntaxException, "An optional parameter cannot be followed by a parameter of the same type."
            # Add the token
            grammar.append(t);
            # 
            if t.isParameter() : 
                paramCount = paramCount + 1
            #                
            last_t = t;
    
    def parse(self, candidates, first, pv) :
        """
         Parse the tokens to see if match with the syntax
      
         @param candidates the candidate tokens to match
         @param first the first item in the tokens list 
         @param pv the parameter validator
         @return <code>null</code> if the candidate does not match, 
                or a <code>ParseData</code> object
         @throws UnknownParameterType
         @see ParseResult
        """
        if candidates == None : 
            raise IllegalArgumentException, "The string array to parse cannot be null."
        if pv == None :
            raise IllegalArgumentException, "Parameter validator cannot be null."
        if first < 0 :
            raise IllegalArgumentException, "First token index have to be 0 or greater."
        ParamValues = [];
        tokenGiven = [];
        ip = 0; # index for paramValues
        it = 0; # index for tokensGiven
        ig = 0; # index for the grammar
        ic = first; # index for the candidates
        varargs = False;
        """
                               increment       
          match opt param   ip  it  ig  ic
            0    0    ?      -   -   -   -   return null
            0    1    0      0   1   1   0    
            0    1    1      1   1   1   0    
            1    ?    0      0   1   1   1         
            1    ?    1      1   1   1   1   
        """
        while ig < len(grammar) and ic < len(candidates) :
            tgrammar = grammar[ig]
            # check if there are varargs
            varargs = tgrammar.isVarArgs()        
            if varargs:
                break;
            match = tgrammar.matches(candidates[ic], pv)
            opt = tgrammar.isOptional()
            param = tgrammar.isParameter()        
            # Validate the token
            if not match and not opt :
                return None
            # Increment ip and add value to paramValues
            if param : 
                if opt and not match :
                    ParamValues.append(None)
                else :
                    ParamValues.append(pv.getParameterType(tgrammar.getParameterTypeName()).convertParameterValue(candidates[ic]))
                ip = ip + 1;
            # Increment ic if matches
            if match : 
                ic = ic + 1;
            # Increment it and ig and add value to tokenGiven
            tokenGiven[it] = match
            it = it + 1
            ig = ig + 1
        # Go for the variable arguments
        if varargs :
            tokenGiven[it] = True
            token = grammar[ig-1]
            ig = ig + 1
            # Validate
            if token.isOptional() :
                raise RuntimeError, "Internal error: Parameter for variable arguments cannot be optional."            
            # Go for values
            while ic < len(candidates) :
                if not token.matches(candidates[ic], pv) :
                    return None
                ParamValues.add(pv.getParameterType(token.getParameterTypeName()).convertParameterValue(candidates[ic]))
                ic = ic + 1
        # Validate ParamValuesAux
        if not varargs and len(ParamValues) > paramCount :
            raise RuntimeError, "Internal error: Invalid parameter count."
        # Determine if the bucle is finished ok
        if ic == len(candidates) and ig == len(grammar) :
            return ParseResult(ParamValues, tokenGiven)
        if ic == len(candidates) and ig == len(grammar)-1 and grammar[len(grammar)-1].isVarArgs() :
            return ParseResult(ParamValues, tokenGiven)
        # Not enough values o matching error 
        return None
