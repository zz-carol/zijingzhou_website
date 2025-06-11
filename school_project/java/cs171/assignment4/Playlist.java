/* THIS CODE WAS MY OWN WORK, IT WAS WRITTEN WITHOUT CONSULTING
    CODE WRITTEN BY OTHER STUDENTS OR ONLINE RESOURCES.
    Carol Zhou   */

/*  This class represents a Playlist of podcast episodes, where each
/*  episode is implemented as an object of type Episode. A user navigating
/*  a Playlist should be able to move between songs using next or previous references.
/*
/*  To enable flexible navigation, the Playlist is implemented as
/*  a Doubly Linked List where each episode has a link to both
/*  the next and the prev episodes in the list.
*/
import java.util.*;

public class Playlist {

	private Episode head;
	private int size; 

	public Playlist() {
		head = null;
		size = 0;
	}

	public boolean isEmpty() {
		return head == null;
	}

	// Ensure that "size" is updated properly in other methods, to always
	// reflect the correct number of episodes in the current Playlist
	public int getSize() {
		return size; 
	}

	// Our implementation of toString() displays the Playlist forward,
	// starting at the first episode (i.e. head) and ending at the last episode,
	// while utilizing the "next" reference in each episode 
	@Override
	public String toString() {
		String output = "[HEAD] ";
		Episode current = head;
		if ( ! isEmpty() ) {
			while( current.next != null ) {
				output += current + " -> ";
				current = current.next;
			}
			output += current + " [END]\n";
		}
		else {
			output += " [END]\n";
		}
		return output;
	}


	// This method displays the Playlist backward, starting at 
	// the last episode and ending at the first episode (i.e. head),
	// while utilizing the "prev" reference in each episode
	public String toReverseString() {
		String output = "[END] ";
		Episode current = head;
		if( ! isEmpty() ) {
			while(current.next != null) 
				current = current.next;
			// current is now pointing to last node
			
			while( current.prev != null ) {
				output += current + " -> ";
				current = current.prev;
			}
			output += current + " [HEAD]\n";
		}
		else {
			output += " [HEAD]\n";
		}
		return output;
	}
	
	
	/**************************************************************/
	// A4 Part 1 Methods (Add/Delete Operations)  
	
	// Create a new Episode using the given title and duration parameters, 
	// then add this Episode properly at the beginning of the current Playlist
	public void addFirst(String title, double duration) {
		if (isEmpty()) { // if the list is empty
			head = new Episode(title, duration, null, null);
		} else { // if the list is not empty (has at least 1 Episode)
			Episode oldHead = head;
			head = new Episode(title, duration, oldHead, null);
			oldHead.prev = head;
		}
		size++;
	}
	
	// Create a new Episode using the given title and duration parameters, 
	// then add this Episode properly at the end of the current Playlist.
	public void addLast(String title, double duration) {
		if (isEmpty()) { // if the list is empty
			head = new Episode(title, duration, null, null);
		} else { // if the list is not empty (has at least 1 Episode)
			Episode oldLast = head;
			while (oldLast.next != null) { // traverse til the end
				oldLast = oldLast.next;
			}
			Episode last = new Episode(title, duration, null, oldLast);
			oldLast.next = last;
		}
		size++;
	}

	// This method should properly delete and return the first Episode in the playlist.
	public Episode deleteFirst() {
		if(isEmpty()) {
			throw new NoSuchElementException();
		} else {
			Episode oldHead = head;
			if (oldHead.next == null) { // if there is only one Episode
				head = null;
			} else { // if there are at least two Episodes
				head = head.next;
				head.prev = null;
			}
			size--;
			return oldHead;
		}
	}
	
	// This method should properly delete and return the last Episode in the playlist.
	public Episode deleteLast() {
		if(isEmpty()) {
			throw new NoSuchElementException();
		} else {
			Episode last = head;
			while (last.next != null) { // traverse til the end
				last = last.next;
			}
			Episode oldLast = last;
			if (last == head) { // if there is only one Episode
				head = null;
			} else { // if there are at least two Episodes
				last.prev.next = null;
			}
			size--;
			return oldLast;
		}
	}
	
	// This method should properly delete and return the Episode with the given title.
	public Episode deleteEpisode(String title) {
		if(isEmpty()) { // if the list is empty
			throw new NoSuchElementException();
		} else {
			Episode previous = head;
			Episode current = head;

			// traverse til we find the Episode with the given title
			while (current != null && !current.title.equals(title)) { 
				previous = current;
				current = current.next;
			}
			
			if (current == head) { // if the matched Episode is the first one
				head = head.next;
				if (head != null) { // if there are at least one Episode after the mathced Episode 
					head.prev = null;
				}
			} else if (current != null) { // if the mathced Episode exists
				previous.next = current.next;
				if (current.next != null) {  // if there are at least one Episode after the mathced Episode 
					current.next.prev = previous;
				}
			} else if (current == null) { // if the Episode title is not found
				throw new NoSuchElementException();
			}
			
			size--;
			return current;
		}
	}


	/***************************************************************/
	// A4 Part 2 Methods (Sorting the Playlist using MergeSort)

	// Merge two sorted lists into one sorted list
	public Episode merge(Episode a, Episode b) {
		Episode first; // the first element of the sorted list
        
        // base case
        if (a == null) { // if list a runs out of elements
            return b;
        } else if (b == null) { // if list b runs out of elements
            return a;
        }
 
        if (a.compareTo(b) <= 0) { // if a smaller or equal to b
            first = a;
            first.next = merge(a.next, b);
        } else { // if a bigger than b
            first = b;
            first.next = merge(a, b.next);
        }
        first.next.prev = first; 

        return first;
	}

	// Finds the middle episode of the list that begins at the passed node reference
	private Episode getMiddleEpisode(Episode node) {
		if(node == null) return node;
		Episode slow = node;
		Episode fast = node;
	    while(fast.next != null && fast.next.next != null) {
	         slow = slow.next;
	         fast = fast.next.next;
	    }
	    return slow;
	 }
	
    // MergeSort starting point
    public void mergeSort() {
	     if( isEmpty() ) throw new RuntimeException("Cannot sort empty list.");
	     head = sort(head);
    }

    // Recursively splits the list starting at a given node reference 
	public Episode sort(Episode node) {
	     if(node == null || node.next == null)
	         return node;
	     Episode middle = getMiddleEpisode(node); //get the middle of the list
	     Episode left_head = node;
	     Episode right_head = middle.next;

	     // split the list into two halves:
	     if(right_head != null) right_head.prev = null;
	     middle.next = null;
	     
	     Episode left = sort(left_head);
	     Episode right = sort(right_head);
	     return merge(left, right);
	 }
	

} // End of Playlist class
