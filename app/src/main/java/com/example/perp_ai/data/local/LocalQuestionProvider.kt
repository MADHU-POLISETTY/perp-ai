package com.example.perp_ai.data.local

import com.example.perp_ai.domain.model.Question
import java.util.UUID

object LocalQuestionProvider {
    fun getQuestionsForCategory(category: String): List<Question> {
        val allQuestions = when (category.lowercase()) {
            "aptitude" -> aptitudeQuestions
            "coding" -> codingQuestions
            "machine learning" -> mlQuestions
            "cloud computing" -> cloudQuestions
            "docker" -> dockerQuestions
            "kubernetes" -> k8sQuestions
            "dbms" -> dbmsQuestions
            "operating systems" -> osQuestions
            "computer networks" -> networkQuestions
            "general interview" -> generalQuestions
            else -> generalQuestions
        }
        return allQuestions.take(5)
    }

    private val aptitudeQuestions = listOf(
        Question(UUID.randomUUID().toString(), "A train running at 60 km/hr crosses a pole in 9s. What is the length of the train?", "120m", "180m", "150m", "300m", "150m", "Aptitude"),
        Question(UUID.randomUUID().toString(), "What is the average of first five multiples of 12?", "36", "38", "40", "42", "36", "Aptitude"),
        Question(UUID.randomUUID().toString(), "The cost price of 20 articles is the same as the selling price of x articles. If the profit is 25%, then the value of x is:", "15", "16", "18", "25", "16", "Aptitude"),
        Question(UUID.randomUUID().toString(), "A person crosses a 600m long street in 5 minutes. What is his speed in km/hr?", "3.6", "7.2", "8.4", "10", "7.2", "Aptitude"),
        Question(UUID.randomUUID().toString(), "If 20% of a = b, then b% of 20 is the same as:", "4% of a", "5% of a", "20% of a", "None of these", "4% of a", "Aptitude")
    )

    private val codingQuestions = listOf(
        Question(UUID.randomUUID().toString(), "Which data structure uses LIFO?", "Queue", "Array", "Stack", "Linked List", "Stack", "Coding"),
        Question(UUID.randomUUID().toString(), "What is the time complexity of searching in a Hash Table (average)?", "O(1)", "O(n)", "O(log n)", "O(n^2)", "O(1)", "Coding"),
        Question(UUID.randomUUID().toString(), "Which of the following is NOT a fundamental OOPS concept?", "Encapsulation", "Compilation", "Inheritance", "Polymorphism", "Compilation", "Coding"),
        Question(UUID.randomUUID().toString(), "In Java, which keyword is used to inherit a class?", "extends", "implements", "inherits", "using", "extends", "Coding"),
        Question(UUID.randomUUID().toString(), "Which of these is used for memory management in Java?", "Garbage Collector", "Compiler", "Debugger", "Virtual Machine", "Garbage Collector", "Coding")
    )

    private val mlQuestions = listOf(
        Question(UUID.randomUUID().toString(), "Which of the following is a Supervised Learning algorithm?", "K-Means", "PCA", "Linear Regression", "Apriori", "Linear Regression", "Machine Learning"),
        Question(UUID.randomUUID().toString(), "What does 'overfitting' mean in ML?", "Model performs well on training and test data", "Model performs well on training but poor on test data", "Model performs poor on both", "Model is too simple", "Model performs well on training but poor on test data", "Machine Learning"),
        Question(UUID.randomUUID().toString(), "Which activation function is commonly used in hidden layers of a Deep Neural Network?", "Sigmoid", "ReLU", "Softmax", "Step", "ReLU", "Machine Learning"),
        Question(UUID.randomUUID().toString(), "In ML, what does 'bias' represent?", "Error from overly simple assumptions", "Error from overly complex models", "Random noise", "None of the above", "Error from overly simple assumptions", "Machine Learning"),
        Question(UUID.randomUUID().toString(), "Which metric is used to evaluate a classification model?", "Mean Squared Error", "R-Squared", "F1 Score", "Mean Absolute Error", "F1 Score", "Machine Learning")
    )

    private val cloudQuestions = listOf(
        Question(UUID.randomUUID().toString(), "What does SaaS stand for?", "Software as a Service", "System as a Service", "Storage as a Service", "Server as a Service", "Software as a Service", "Cloud Computing"),
        Question(UUID.randomUUID().toString(), "Which AWS service is used for scalable storage?", "EC2", "RDS", "S3", "Lambda", "S3", "Cloud Computing"),
        Question(UUID.randomUUID().toString(), "In cloud computing, what is 'elasticity'?", "Fixed resource allocation", "Ability to scale resources up and down", "Connecting to multiple clouds", "Hardware durability", "Ability to scale resources up and down", "Cloud Computing"),
        Question(UUID.randomUUID().toString(), "Which of these is a Serverless computing service?", "Azure VMs", "AWS Lambda", "Google Compute Engine", "EC2", "AWS Lambda", "Cloud Computing"),
        Question(UUID.randomUUID().toString(), "What is a 'Region' in AWS?", "A single data center", "A logical group of data centers in a geographic area", "A virtual private network", "A type of storage", "A logical group of data centers in a geographic area", "Cloud Computing")
    )

    private val dockerQuestions = listOf(
        Question(UUID.randomUUID().toString(), "What is a Docker image?", "A running container", "A read-only template for creating containers", "A network protocol", "A storage volume", "A read-only template for creating containers", "Docker"),
        Question(UUID.randomUUID().toString(), "Which command is used to list all running Docker containers?", "docker ps", "docker images", "docker run", "docker stop", "docker ps", "Docker"),
        Question(UUID.randomUUID().toString(), "What is the default filename used for Docker builds?", "Buildfile", "Containerfile", "Dockerfile", "Manifest.yml", "Dockerfile", "Docker"),
        Question(UUID.randomUUID().toString(), "Which Docker instruction defines the starting command for the container?", "START", "RUN", "ENTRYPOINT", "COPY", "ENTRYPOINT", "Docker"),
        Question(UUID.randomUUID().toString(), "What is 'Docker Hub'?", "A local storage", "A public registry for Docker images", "A networking tool", "A monitoring tool", "A public registry for Docker images", "Docker")
    )

    private val k8sQuestions = listOf(
        Question(UUID.randomUUID().toString(), "What is the smallest deployable unit in Kubernetes?", "Container", "Pod", "Node", "Service", "Pod", "Kubernetes"),
        Question(UUID.randomUUID().toString(), "Which K8s component manages the state of the cluster?", "Kubelet", "Kube-proxy", "etcd", "Docker", "etcd", "Kubernetes"),
        Question(UUID.randomUUID().toString(), "Which command line tool is used to interact with K8s cluster?", "docker-cli", "k8s-cli", "kubectl", "kube-admin", "kubectl", "Kubernetes"),
        Question(UUID.randomUUID().toString(), "What is a 'Service' in Kubernetes?", "A database", "A load balancer to expose a set of Pods", "A backup tool", "A worker node", "A load balancer to expose a set of Pods", "Kubernetes"),
        Question(UUID.randomUUID().toString(), "Which component is responsible for maintaining network rules on nodes?", "Scheduler", "API Server", "Kube-proxy", "Controller Manager", "Kube-proxy", "Kubernetes")
    )

    private val dbmsQuestions = listOf(
        Question(UUID.randomUUID().toString(), "What does ACID stand for in DBMS?", "Atomicity, Consistency, Isolation, Durability", "Access, Control, Integrated, Data", "Automated, Centralized, Indexed, Distributed", "None of these", "Atomicity, Consistency, Isolation, Durability", "DBMS"),
        Question(UUID.randomUUID().toString(), "Which SQL command is used to remove all records from a table?", "DELETE", "REMOVE", "TRUNCATE", "DROP", "TRUNCATE", "DBMS"),
        Question(UUID.randomUUID().toString(), "What is a 'Primary Key'?", "A key that can be null", "A unique identifier for each record in a table", "A key that connects two tables", "A backup key", "A unique identifier for each record in a table", "DBMS"),
        Question(UUID.randomUUID().toString(), "Which join returns all records when there is a match in either left or right table?", "INNER JOIN", "LEFT JOIN", "RIGHT JOIN", "FULL OUTER JOIN", "FULL OUTER JOIN", "DBMS"),
        Question(UUID.randomUUID().toString(), "What is 'Normalization' in databases?", "Adding more data", "Reducing data redundancy", "Backing up data", "Indexing data", "Reducing data redundancy", "DBMS")
    )

    private val osQuestions = listOf(
        Question(UUID.randomUUID().toString(), "What is 'Thrashing' in an OS?", "High paging activity", "System shutdown", "High disk speed", "High CPU speed", "High paging activity", "Operating Systems"),
        Question(UUID.randomUUID().toString(), "Which scheduling algorithm can lead to starvation?", "Round Robin", "First Come First Serve", "Shortest Job First", "All of these", "Shortest Job First", "Operating Systems"),
        Question(UUID.randomUUID().toString(), "What is a 'Semaphore'?", "A hardware component", "A variable used to solve critical section problems", "A type of memory", "A scheduling algorithm", "A variable used to solve critical section problems", "Operating Systems"),
        Question(UUID.randomUUID().toString(), "What is 'Virtual Memory'?", "A physical RAM", "Technique that allows execution of processes larger than physical memory", "Memory on a CD", "Fastest memory", "Technique that allows execution of processes larger than physical memory", "Operating Systems"),
        Question(UUID.randomUUID().toString(), "What is the 'Kernel' in an OS?", "The user interface", "The core part that manages hardware and software interactions", "A file system", "A web browser", "The core part that manages hardware and software interactions", "Operating Systems")
    )

    private val networkQuestions = listOf(
        Question(UUID.randomUUID().toString(), "Which layer in OSI model is responsible for routing?", "Physical", "Data Link", "Network", "Transport", "Network", "Computer Networks"),
        Question(UUID.randomUUID().toString(), "What is the port number for HTTP?", "21", "25", "80", "443", "80", "Computer Networks"),
        Question(UUID.randomUUID().toString(), "What does DNS stand for?", "Domain Name System", "Distributed Network Service", "Digital Network Security", "Domain Node Server", "Domain Name System", "Computer Networks"),
        Question(UUID.randomUUID().toString(), "Which protocol is 'connectionless'?", "TCP", "UDP", "HTTP", "FTP", "UDP", "Computer Networks"),
        Question(UUID.randomUUID().toString(), "What is the size of an IPv4 address?", "32 bits", "64 bits", "128 bits", "16 bits", "32 bits", "Computer Networks")
    )

    private val generalQuestions = listOf(
        Question(UUID.randomUUID().toString(), "What is your greatest strength?", "I am a quick learner", "I have no strengths", "I sleep a lot", "I am rich", "I am a quick learner", "General Interview"),
        Question(UUID.randomUUID().toString(), "Why should we hire you?", "I am motivated and have the right skills", "I need money", "I am the boss", "My friend works here", "I am motivated and have the right skills", "General Interview"),
        Question(UUID.randomUUID().toString(), "Where do you see yourself in 5 years?", "In a lead role contributing to company growth", "At home", "On vacation", "I don't know", "In a lead role contributing to company growth", "General Interview"),
        Question(UUID.randomUUID().toString(), "How do you handle pressure?", "Stay calm and prioritize tasks", "I cry", "I quit", "I ignore it", "Stay calm and prioritize tasks", "General Interview"),
        Question(UUID.randomUUID().toString(), "What is your weakness?", "I focus too much on details (Perfectionist)", "I am lazy", "I am late", "None", "I focus too much on details (Perfectionist)", "General Interview")
    )
}
