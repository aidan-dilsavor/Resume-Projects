#These are two functions to encode and then decode a string using the Rail Fence Cipher. 
#This cipher is used to encode a string by placing each character successively 
#in a diagonal along a set of "rails". First start off moving diagonally and down. 
#When you reach the bottom, reverse direction and move diagonally and up until 
#you reach the top rail. Continue until you reach the end of the string. 
#Each "rail" is then read left to right to derive the encoded string.


def encode_rail_fence_cipher(string, n):
    #Initializing variables
    finishedString = ""
    distance = (n - 1) * 2
    intervals = int(len(string) / distance)  
    remainder = len(string) % distance
    i = 0
    encodedString = ""
    #This first loop and if statement generates the first encoded row
    while i < intervals:
        
        finishedString += string[i * distance]
        i += 1
    if remainder > 0:
        
        finishedString += string[distance * intervals]
    

    i = 0
    j = 0
    middleRails = n - 2
    firstLetter = 1
    lastLetter = distance - 1  
    #Two while loops and if statements are for the middle rows of the encoding system
    while j < middleRails:
        
        while i < intervals:
            
            finishedString += string[firstLetter + (distance * i)]
            finishedString += string[lastLetter + (distance * i)]
            i += 1       
        if(remainder > firstLetter):
            
            finishedString += string[firstLetter + (distance * intervals)]
        if(remainder > lastLetter):
            
            finishedString += string[lastLetter + (distance * intervals)]   
        i = 0
        firstLetter += 1
        lastLetter -= 1
        j += 1  
        
    #Final while loop and if statement generate the final row in the encoding system
    while i < intervals:
        
        finishedString += string[ int(distance / 2) + (i * distance) ]
        i += 1
    if remainder >= n:
        
        finishedString += string[distance * intervals + n - 1]
        
    return finishedString




def decode_rail_fence_cipher(string, n):
    
    #Initializing variables
    finishedString = ""
    distance = (n - 1) * 2
    intervals = int(len(string) / distance)  
    remainder = len(string) % distance
    
    #First if-else creates the first two indices that will give 
    #us the desired characters from the string we desire to decode
    if remainder > 0:
        
        arrayIndices = [0, intervals + 1]
    else:
        
        arrayIndices = [0, intervals]
        
    #While loops generate the desired indices of the other characters in the first interval
    i = 1
    while i < (n - 1):
        
        arrayIndices.append(arrayIndices[i] + (2 * intervals))
        if remainder > i:
            
            arrayIndices[i+1] += 1
        if remainder - (n - 1 - i) >= n:
            
            arrayIndices[i+1] += 1
        i += 1

    i = n - 2
    while i > 0:
        
        arrayIndices.append(arrayIndices[i] + 1)
        i -= 1
        
    #Once I know the first interval indices, I know the indices of all following intervals
    i = 0
    finalString = ""   
    while i < intervals:

        k = 0
        while k < len(arrayIndices):
            
            finalString += string[arrayIndices[k]]
            arrayIndices[k] += 2
            k += 1
        arrayIndices[0] -= 1
        arrayIndices[int(distance / 2)] -= 1
        i += 1
        
    i = 0
    
    #Final two while loops account for the remainder interval, 
    #as the remainder disrupts the algorithm I have above
    while i < distance - remainder:
        
        arrayIndices.pop()
        i += 1

    k = 0
    while k < len(arrayIndices):
        
        finalString += string[arrayIndices[k]]
        arrayIndices[k] += 2
        k += 1
           
    return finalString
