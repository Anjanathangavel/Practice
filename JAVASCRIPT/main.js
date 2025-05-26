// 1. JavaScript Basics & Setup
console.log("Welcome to the Community Portal");
window.addEventListener('load', () => {
    alert("Page fully loaded! Welcome to our Community Event Portal.");
});

// 2. Syntax, Data Types, and Operators
class Event {
    constructor(name, date, category, location, maxSeats) {
        this.name = name;
        this.date = date;
        this.category = category;
        this.location = location;
        this.maxSeats = maxSeats;
        this.availableSeats = maxSeats;
        this.registrations = [];
    }

    // 5. Objects and Prototypes
    checkAvailability() {
        return this.availableSeats > 0 && new Date(this.date) > new Date();
    }

    registerUser(user) {
        if (this.checkAvailability()) {
            this.registrations.push(user);
            this.availableSeats--;
            return true;
        }
        return false;
    }

    getEventInfo() {
        return `${this.name} on ${this.date} at ${this.location}. ${this.availableSeats}/${this.maxSeats} seats available.`;
    }
}

// 6. Arrays and Methods
let events = [
    new Event("Jazz Night", "2023-12-15", "music", "Community Center", 100),
    new Event("Baking Workshop", "2023-11-20", "workshop", "Local Bakery", 15),
    new Event("Basketball Tournament", "2023-12-10", "sports", "High School Gym", 50),
    new Event("Art Exhibition", "2023-11-25", "art", "Gallery Downtown", 200)
];

// 4. Functions, Scope, Closures, Higher-Order Functions
function addEvent(name, date, category, location, maxSeats) {
    const newEvent = new Event(name, date, category, location, maxSeats);
    events.push(newEvent);
    return newEvent;
}

function registerUser(eventId, userName, userEmail) {
    try {
        const event = events.find(e => e.name === eventId);
        if (!event) throw new Error("Event not found");
        
        const user = { name: userName, email: userEmail };
        if (event.registerUser(user)) {
            updateUI();
            return true;
        }
        throw new Error("No available seats or event has passed");
    } catch (error) {
        console.error("Registration failed:", error.message);
        alert(`Registration failed: ${error.message}`);
        return false;
    }
}

// Closure to track category registrations
function createCategoryTracker() {
    const categoryCounts = {};
    
    return function(category) {
        categoryCounts[category] = (categoryCounts[category] || 0) + 1;
        console.log(`Total registrations for ${category}: ${categoryCounts[category]}`);
        return categoryCounts[category];
    };
}

const trackCategoryRegistration = createCategoryTracker();

// Higher-order function for filtering
function filterEvents(criteria, callback) {
    const filtered = events.filter(criteria);
    callback(filtered);
}

// 3. Conditionals, Loops, and Error Handling
function displayEvents(eventsToDisplay = events) {
    const eventsList = document.getElementById('eventsList');
    eventsList.innerHTML = '';

    eventsToDisplay.forEach(event => {
        // Only show upcoming events with available seats
        if (event.checkAvailability()) {
            const eventCard = document.createElement('div');
            eventCard.className = 'event-card';
            
            eventCard.innerHTML = `
                <h3>${event.name}</h3>
                <p><strong>Date:</strong> ${new Date(event.date).toLocaleDateString()}</p>
                <p><strong>Location:</strong> ${event.location}</p>
                <p><strong>Category:</strong> ${event.category}</p>
                <p><strong>Seats:</strong> ${event.availableSeats}/${event.maxSeats}</p>
                <button class="register-btn" data-event="${event.name}">Register</button>
            `;
            
            eventsList.appendChild(eventCard);
        }
    });

    // 8. Event Handling
    document.querySelectorAll('.register-btn').forEach(btn => {
        btn.onclick = function() {
            const eventName = this.getAttribute('data-event');
            document.getElementById('selectedEvent').value = eventName;
            document.getElementById('registrationForm').scrollIntoView({ behavior: 'smooth' });
        };
    });
}

// 7. DOM Manipulation
function updateUI() {
    displayEvents();
    populateEventDropdown();
}

function populateEventDropdown() {
    const select = document.getElementById('selectedEvent');
    select.innerHTML = '<option value="">Select an event</option>';
    
    events.forEach(event => {
        if (event.checkAvailability()) {
            const option = document.createElement('option');
            option.value = event.name;
            option.textContent = event.getEventInfo();
            select.appendChild(option);
        }
    });
}

// 9. Async JS, Promises, Async/Await
async function fetchMockEvents() {
    const spinner = document.getElementById('loadingSpinner');
    spinner.style.display = 'block';
    
    try {
        // Simulate API call with delay
        await new Promise(resolve => setTimeout(resolve, 1000));
        
        // Mock response
        const mockEvents = [
            { name: "Book Club Meeting", date: "2023-12-05", category: "workshop", location: "Library", maxSeats: 20 },
            { name: "Holiday Concert", date: "2023-12-20", category: "music", location: "Town Hall", maxSeats: 150 }
        ];
        
        mockEvents.forEach(event => {
            addEvent(event.name, event.date, event.category, event.location, event.maxSeats);
        });
        
        updateUI();
    } catch (error) {
        console.error("Failed to fetch events:", error);
        alert("Failed to load additional events. Please try again later.");
    } finally {
        spinner.style.display = 'none';
    }
}

// 10. Modern JavaScript Features
function processEvent({ name, date, category, location }) {
    const defaultCategory = category || 'general';
    const processedDate = date || new Date().toISOString().split('T')[0];
    
    return {
        name: name.toUpperCase(),
        date: processedDate,
        category: defaultCategory,
        location: location || 'TBD'
    };
}

// 11. Working with Forms
document.getElementById('eventForm').addEventListener('submit', function(e) {
    e.preventDefault();
    
    const name = this.elements.eventName.value;
    const date = this.elements.eventDate.value;
    const category = this.elements.eventCategory.value;
    const location = this.elements.eventLocation.value;
    const seats = parseInt(this.elements.eventSeats.value);
    
    addEvent(name, date, category, location, seats);
    updateUI();
    this.reset();
});

document.getElementById('registrationForm').addEventListener('submit', function(e) {
    e.preventDefault();
    
    const name = this.elements.userName.value;
    const email = this.elements.userEmail.value;
    const eventName = this.elements.selectedEvent.value;
    
    if (registerUser(eventName, name, email)) {
        trackCategoryRegistration(events.find(e => e.name === eventName).category);
        this.reset();
        alert("Registration successful!");
    }
});

// 12. AJAX & Fetch API
function submitRegistrationToServer(userData) {
    const spinner = document.getElementById('loadingSpinner');
    spinner.style.display = 'block';
    
    // Mock API endpoint
    const mockApiUrl = 'https://jsonplaceholder.typicode.com/posts';
    
    fetch(mockApiUrl, {
        method: 'POST',
        body: JSON.stringify(userData),
        headers: {
            'Content-type': 'application/json; charset=UTF-8',
        },
    })
    .then(response => response.json())
    .then(data => {
        console.log('Registration submitted:', data);
        alert('Registration submitted successfully!');
    })
    .catch(error => {
        console.error('Error submitting registration:', error);
        alert('Error submitting registration. Please try again.');
    })
    .finally(() => {
        spinner.style.display = 'none';
    });
}

// 13. Debugging and Testing
// Example debug function
function debugFormSubmission(formData) {
    console.group('Form Submission Debug');
    console.log('Form data:', formData);
    console.log('Current events:', events);
    console.groupEnd();
}

// 14. jQuery and JS Frameworks
$(document).ready(function() {
    // jQuery click handler
    $('#eventsList').on('click', '.register-btn', function() {
        $(this).fadeOut(200).fadeIn(200);
    });
    
    // jQuery for animations
    $('.event-card').hover(
        function() { $(this).css('box-shadow', '0 5px 15px rgba(0,0,0,0.2)'); },
        function() { $(this).css('box-shadow', '0 2px 4px rgba(0,0,0,0.1)'); }
    );
});

// Event listeners for filters
document.getElementById('searchInput').addEventListener('keydown', function() {
    const searchTerm = this.value.toLowerCase();
    filterEvents(
        event => event.name.toLowerCase().includes(searchTerm) && event.checkAvailability(),
        displayEvents
    );
});

document.getElementById('categoryFilter').addEventListener('change', function() {
    const category = this.value;
    if (category === 'all') {
        displayEvents();
    } else {
        filterEvents(
            event => event.category === category && event.checkAvailability(),
            displayEvents
        );
    }
});

// Initialize the app
updateUI();
fetchMockEvents();

// Benefit of frameworks like React or Vue:
// They provide component-based architecture which makes the code more modular,
// easier to maintain, and allows for better state management in complex applications.